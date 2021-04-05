package com.microservicearchitecture.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
    private final int code;
    private final String message;
}
