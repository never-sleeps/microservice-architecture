package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.CreateOrderRequest;
import com.microservicearchitecture.dto.request.CreateWithdrawRequest;
import com.microservicearchitecture.dto.response.CreateOrderResponse;
import com.microservicearchitecture.dto.response.GetUserResponse;
import com.microservicearchitecture.exceptions.AccountException;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.client.BillingClient;
import com.microservicearchitecture.client.UserClient;
import com.microservicearchitecture.messaging.OrderCreatedEvent;
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
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        GetUserResponse getUserResponse = userClient.findUserById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%d not found", request.getUserId())));

        CreateWithdrawRequest withdrawRequest = CreateWithdrawRequest.builder()
                .amount(request.getPrice())
                .build();

        boolean successPayment = billingClient.withdrawMoney(request.getUserId(), withdrawRequest);
        if (!successPayment) {
            log.info("Ошибка оплаты для userId=" + request.getUserId() + ", price=" + request.getPrice());
            sendNotification(null, getUserResponse.getEmail(), request.getUserId(), "FAILURE");
            throw new AccountException("Ошибка оплаты для userId=" + request.getUserId() + ", price=" + request.getPrice());
        }
        log.info("Произведена оплата для userId=" + request.getUserId() + ", price=" + request.getPrice());

        OrderEntity entity = new OrderEntity();
        entity.setPrice(request.getPrice());
        entity.setUserId(request.getUserId());
        entity = orderRepository.save(entity);
        log.info("Создан заказ для userId=" + request.getUserId() + ", price=" + request.getPrice());
        sendNotification(entity.getId(), getUserResponse.getEmail(), request.getUserId(), "SUCCESS");

        return new CreateOrderResponse(entity);
    }

    public List<CreateOrderResponse> findAllOrders() {
        log.info("Получение списка всех заказов");
        return orderRepository.findAll().stream()
                .map(CreateOrderResponse::new)
                .collect(Collectors.toList());
    }

    private void sendNotification(Long orderId, String email, Long userId, String status) {
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(orderId)
                .email(email)
                .userId(userId)
                .status(status)
                .build();
        kafkaTemplate.send(ORDER_TOPIC, orderCreatedEvent);
        log.info("Отправлено уведомление для userId=" + userId + ": " + email);
    }
}
