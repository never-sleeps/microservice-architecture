package com.microservicearchitecture.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthResponseDto {
    private final String status;
}
