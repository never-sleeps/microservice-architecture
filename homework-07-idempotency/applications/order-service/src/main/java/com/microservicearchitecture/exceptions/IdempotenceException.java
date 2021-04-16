package com.microservicearchitecture.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdempotenceException extends RuntimeException {
    public IdempotenceException(String message) {
        super(message);
    }
}
