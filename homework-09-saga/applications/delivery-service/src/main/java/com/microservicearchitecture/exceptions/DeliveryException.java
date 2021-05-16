package com.microservicearchitecture.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryException extends RuntimeException {
    private final String message;
}
