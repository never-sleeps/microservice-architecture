package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.CreateOrderRequest;
import com.microservicearchitecture.dto.request.CreateWithdrawRequest;
import com.microservicearchitecture.dto.response.UserDto;
import com.microservicearchitecture.exceptions.AccountBalanceException;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.client.BillingClient;
import com.microservicearchitecture.client.UserClient;
import com.microservicearchitecture.messaging.OrderCreatedEvent;
import com.microservicearchitecture.persistence.entity.Order;
import com.microservicearchitecture.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_TOPIC = "order_notification";

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final BillingClient billingClient;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    /**
     * 1. Получаем данные пользователя.
     * 2. Снимаем деньги с пользователя с помощью сервиса биллинга.
     * 3. Отсылаем пользователю уведомление с результатами оформления заказа.
     * Если биллинг подтвердил платеж, должно отправиться письмо счастья. Если нет, то письмо горя.
     * @param request true, если оплата прошла успешно
     */
    @Override
    @Transactional
    public void createOrder(CreateOrderRequest request) {
        UserDto userDto = userClient.findUserById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User not found by id: %s", request.getUserId())
                ));

        CreateWithdrawRequest withdrawRequest = CreateWithdrawRequest.builder()
                .amount(request.getPrice())
                .build();

        boolean successPayment = billingClient.withdrawMoney(request.getUserId(), withdrawRequest);
        if (!successPayment) {
            sendNotification(null, userDto.getMail(), request.getUserId(), "FAILURE");
            throw new AccountBalanceException(
                    String.format("Account balance for userId=%s is lower than: %s", request.getUserId(), request.getPrice())
            );
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .price(request.getPrice())
                .build();
        order = orderRepository.save(order);

        sendNotification(order.getId(), userDto.getMail(), request.getUserId(), "SUCCESS");
    }

    /**
     * Отправляем уведомление пользователю
     * @param orderId id заказа
     * @param mail почта пользователя
     * @param userId id пользователя
     * @param status "SUCCESS"/"FAILURE"
     */
    private void sendNotification(Long orderId, String mail, Long userId, String status) {
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(orderId)
                .mail(mail)
                .userId(userId)
                .status(status)
                .build();
        kafkaTemplate.send(ORDER_TOPIC, orderCreatedEvent);
    }
}
