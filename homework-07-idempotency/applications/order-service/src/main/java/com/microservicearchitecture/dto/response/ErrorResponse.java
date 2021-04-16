package com.microservicearchitecture.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final int code;
    private final String message;
}
