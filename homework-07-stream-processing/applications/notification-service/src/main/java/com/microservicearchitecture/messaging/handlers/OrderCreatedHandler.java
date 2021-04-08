package com.microservicearchitecture.messaging.handlers;

import com.microservicearchitecture.messaging.DomainEventHandler;
import com.microservicearchitecture.messaging.OrderCreatedEvent;
import com.microservicearchitecture.persistence.entity.NotificationEntity;
import com.microservicearchitecture.persistence.repository.NotificationRepository;
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
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setStatus(event.getStatus());
        notificationEntity.setOrderId(event.getOrderId());
        notificationEntity.setEmail(event.getEmail());
        notificationEntity.setUserId(event.getUserId());
        notificationRepository.save(notificationEntity);
    }
}
