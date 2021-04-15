package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.response.DtoHealthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping
    public ResponseEntity<?> status() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public DtoHealthResponse healthCheck() {
        return DtoHealthResponse.builder()
                .status(HttpStatus.OK.name())
                .build();
    }
}
