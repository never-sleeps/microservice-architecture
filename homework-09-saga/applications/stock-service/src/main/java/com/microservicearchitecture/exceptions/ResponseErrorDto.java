package com.microservicearchitecture.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseErrorDto {
    private final int code;
    private final String message;
}
