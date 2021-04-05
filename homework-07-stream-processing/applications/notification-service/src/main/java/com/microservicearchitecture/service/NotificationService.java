package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.GetNotificationResponse;

import java.util.List;

public interface NotificationService {
    List<GetNotificationResponse> findAllNotifications();
}
