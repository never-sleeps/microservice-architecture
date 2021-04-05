package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.GetNotificationResponse;
import com.microservicearchitecture.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Получение всех уведомлений
     * @return все уведомления
     */
    @GetMapping("/notifications")
    public List<GetNotificationResponse> findAllNotifications() {
        return notificationService.findAllNotifications();
    }
}
