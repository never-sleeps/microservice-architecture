package com.microservicearchitecture.messaging.handlers;

import com.microservicearchitecture.messaging.DomainEventHandler;
import com.microservicearchitecture.messaging.OrderCreatedEvent;
import com.microservicearchitecture.persistence.entity.Notification;
import com.microservicearchitecture.persistence.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedHandler implements DomainEventHandler<OrderCreatedEvent> {

    private final NotificationRepository notificationRepository;

    @Autowired
    public OrderCreatedHandler(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void process(OrderCreatedEvent event) {
        Notification entity = Notification.builder()
                .status(event.getStatus())
                .orderId(event.getOrderId())
                .mail(event.getMail())
                .userId(event.getUserId())
                .build();
        notificationRepository.save(entity);
    }
}
