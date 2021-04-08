package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.GetNotificationResponse;
import com.microservicearchitecture.persistence.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<GetNotificationResponse> getNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(GetNotificationResponse::new)
                .collect(Collectors.toList());
    }
}
