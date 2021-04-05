package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.GetNotificationResponse;
import com.microservicearchitecture.persistence.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public final class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    @Override
    public List<GetNotificationResponse> findAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(GetNotificationResponse::new)
                .collect(Collectors.toList());
    }
}
