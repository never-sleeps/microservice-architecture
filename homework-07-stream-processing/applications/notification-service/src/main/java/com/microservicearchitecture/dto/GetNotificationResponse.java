package com.microservicearchitecture.dto;

import com.microservicearchitecture.persistence.entity.NotificationEntity;
import lombok.Getter;

@Getter
public class GetNotificationResponse {
    private final String state;
    private final Long orderId;
    private final Long userId;
    private final String email;

    public GetNotificationResponse(NotificationEntity entity) {
        this.state = entity.getStatus();
        this.orderId = entity.getOrderId();
        this.userId = entity.getUserId();
        this.email = entity.getEmail();
    }
}
