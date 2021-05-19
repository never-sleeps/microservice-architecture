package com.microservicearchitecture.service;

import com.microservicearchitecture.client.delivery.DeliveryClient;
import com.microservicearchitecture.client.delivery.dto.DeliveryRequest;
import com.microservicearchitecture.client.stock.StockClient;
import com.microservicearchitecture.client.stock.dto.ReserveRequest;
import com.microservicearchitecture.client.stock.dto.UnreserveRequest;
import com.microservicearchitecture.dto.request.CreateOrderRequest;
import com.microservicearchitecture.client.billing.dto.CreateWithdrawRequest;
import com.microservicearchitecture.client.billing.dto.DepositMoneyRequest;
import com.microservicearchitecture.dto.response.CreateOrderResponse;
import com.microservicearchitecture.client.user.dto.UserResponse;
import com.microservicearchitecture.enumerations.NotificationType;
import com.microservicearchitecture.enumerations.OrderStatus;
import com.microservicearchitecture.exceptions.AccountException;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.client.billing.BillingClient;
import com.microservicearchitecture.client.user.UserClient;
import com.microservicearchitecture.messaging.OrderEvent;
import com.microservicearchitecture.persistence.entity.OrderEntity;
import com.microservicearchitecture.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String ORDER_TOPIC = "order_notification";

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final BillingClient billingClient;
    private final StockClient stockClient;
    private final DeliveryClient deliveryClient;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        // Получаем информацию о клиенте.
        UserResponse userResponse = userClient.findUserById(request.getUserId())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("User with id=%d not found", request.getUserId()))
                );

        OrderEntity orderEntity = createOrderSaga(request, userResponse);
        return new CreateOrderResponse(orderEntity);
    }

    /**
     * Создание заказа с использование паттерна САГА
     * 1. Сохранение заказа в статусе PENDING
     * 2. Оплата заказа, перевод заказа в статус PAID:
     *          В случае ошибки:
     *              Перевод заказа в статус CANCELLED
     *              Отправка уведомления об ошибке оплаты
     *
     * 3. Резервирование товаров на складе, перевод заказа в статус RESERVED_IN_STOCK
     *          В случае ошибки:
     *              Перевод заказа в статус CANCELLED
     *              Отмена оплаты заказа
     *              Отправка уведомления об ошибке резервирования
     * 4. Доставка заказа клиенту, перевод заказа в статус DELIVERED_TO_CLIENT
     *          В случае ошибки:
     *              Перевод заказа в статус CANCELLED
     *              Отмена резервирования товара на складе
     *              Отмена оплаты заказа
     *              Отправка уведомления об ошибке доставки
     *
     * @param request данные заказа
     * @param userResponse данные клиента
     * @return сущность заказа
     */
    private OrderEntity createOrderSaga(CreateOrderRequest request, UserResponse userResponse) {
        OrderEntity order = new OrderEntity();
        order.setPrice(request.getPrice());
        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.saveAndFlush(order);

        boolean isSuccessPayment = makePayment(request);
        if (!isSuccessPayment) {
            log.info("Ошибка оплаты для orderId=" + order.getId());
            cancelOrder(order);
            sendNotification(order.getId(), userResponse.getEmail(), userResponse.getId(), NotificationType.PAYMENT_ERROR);
            throw new AccountException("Ошибка оплаты для orderId=" + order.getId());
        }
        log.info("Произведена оплата для orderId=" + order.getId());
        order = transferOrderToStatus(order, OrderStatus.PAID);

        boolean isSuccessReserving = reserveInStock(request);
        if (!isSuccessReserving) {
            log.info("Ошибка резервирования товара на складе для orderId=" + order.getId());
            cancelOrder(order);
            cancelPayment(request);
            sendNotification(order.getId(), userResponse.getEmail(), userResponse.getId(), NotificationType.RESERVATION_ERROR);
            throw new AccountException("Ошибка резервирования товара на складе для orderId=" + order.getId());
        }
        log.info("Произведено резервирования товара на складе для orderId=" + order.getId());
        order = transferOrderToStatus(order, OrderStatus.RESERVED_IN_STOCK);

        boolean isSuccessDelivery = makeDelivery(request);
        if (!isSuccessDelivery) {
            log.info("Ошибка доставки заказа для orderId=" + order.getId());
            cancelOrder(order);
            cancelReservingInStock(request);
            cancelPayment(request);
            sendNotification(order.getId(), userResponse.getEmail(), userResponse.getId(), NotificationType.DELIVERY_ERROR);
            throw new AccountException("Ошибка доставки заказа для orderId=" + order.getId());
        }
        log.info("Произведена доставка заказа для orderId=" + order.getId());
        order = transferOrderToStatus(order, OrderStatus.DELIVERED_TO_CLIENT);
        sendNotification(order.getId(), userResponse.getEmail(), userResponse.getId(), NotificationType.SUCCESS);

        return order;
    }

    /**
     * Производим оплату заказа
     * @param request данные заказа
     * @return true в случае успешной оплаты, false в случае ошибки
     */
    private boolean makePayment(CreateOrderRequest request) {
        // Создаём сущность списания средств.
        CreateWithdrawRequest withdrawRequest = CreateWithdrawRequest.builder()
                .amount(request.getPrice())
                .build();
        // Производим списание
        return billingClient.withdrawMoney(request.getUserId(), withdrawRequest);
    }

    /**
     * Отменяем оплату заказа
     * @param request данные заказа
     */
    private void cancelPayment(CreateOrderRequest request) {
        // Создаём сущность возврата средств.
        DepositMoneyRequest depositMoneyRequest = DepositMoneyRequest.builder()
                .amount(request.getPrice())
                .build();
        billingClient.depositMoney(request.getUserId(), depositMoneyRequest);
    }

    /**
     * Передаём информацию о заказе на склад для резервирования товаров
     * @param request данные заказа
     * @return true в случае успешного резервирования, false  в случае ошибки
     */
    private boolean reserveInStock(CreateOrderRequest request) {
        ReserveRequest reserveRequest = ReserveRequest.builder()
                .price(request.getPrice())
                .build();
        return stockClient.reserve(reserveRequest);
    }

    /**
     * Отменяем резервирование товаров на складе
     * @param request данные заказа
     */
    private void cancelReservingInStock(CreateOrderRequest request) {
        UnreserveRequest unreserveRequest = UnreserveRequest.builder().build();
        stockClient.unreserve(unreserveRequest);
    }

    /**
     * Доставка товара
     * @param request данные заказа
     * @return true в случае успешного резервирования, false  в случае ошибки
     */
    private boolean makeDelivery(CreateOrderRequest request) {
        DeliveryRequest deliveryRequest = DeliveryRequest.builder()
                .address(request.getDeliveryAddress())
                .build();
        return deliveryClient.delivery(deliveryRequest);
    }


    private void cancelOrder(OrderEntity order){
        transferOrderToStatus(order, OrderStatus.CANCELLED);
    }

    private OrderEntity transferOrderToStatus(OrderEntity order, OrderStatus status) {
        log.info(String.format("Изменение статуса заказа: orderId=%s, %s > %s", order.getId(), order.getStatus(), status));
        order.setStatus(status);
        return orderRepository.saveAndFlush(order);
    }

    /**
     * Получение списка всех заказов
     * @return список всех заказов
     */
    public List<CreateOrderResponse> findAllOrders() {
        log.info("Получение списка всех заказов");
        return orderRepository.findAll().stream()
                .map(CreateOrderResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Получение заказа по id
     * @return заказ
     */
    public CreateOrderResponse findOrderById(Long id) {
        log.info("Получение заказа по id");
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id=%d not found", id)));
        return new CreateOrderResponse(orderEntity);
    }

    /**
     * Отправка уведомления
     * @param orderId id заказа
     * @param email email клиента
     * @param userId id клиента
     * @param status статус создания заказа
     */
    private void sendNotification(Long orderId, String email, Long userId, NotificationType status) {
        OrderEvent orderCreatedEvent = OrderEvent.builder()
                .orderId(orderId)
                .email(email)
                .userId(userId)
                .status(status)
                .build();
        kafkaTemplate.send(ORDER_TOPIC, orderCreatedEvent);
        log.info("Отправлено уведомление для userId=" + userId + ": " + email);
    }
}
