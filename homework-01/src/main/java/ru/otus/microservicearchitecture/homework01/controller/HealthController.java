package ru.otus.microservicearchitecture.homework01.controller;

import ru.otus.microservicearchitecture.homework01.entity.HealthStatusEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public HealthStatusEntity getHealth() {
        HealthStatusEntity healthStatus = new HealthStatusEntity();
        healthStatus.setStatus("OK");
        return healthStatus;
    }
}
