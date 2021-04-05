package com.microservicearchitecture.dto;

import com.microservicearchitecture.persistence.entity.Notification;
import lombok.Data;

@Data
public class GetNotificationResponse {
    private final String state;
    private final Long orderId;
    private final Long userId;
    private final String mail;

    public GetNotificationResponse(Notification notification) {
        this.state = notification.getStatus();
        this.orderId = notification.getOrderId();
        this.userId = notification.getUserId();
        this.mail = notification.getMail();
    }
}
