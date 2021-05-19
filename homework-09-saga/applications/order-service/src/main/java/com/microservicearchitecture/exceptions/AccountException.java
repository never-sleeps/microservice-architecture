package com.microservicearchitecture.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountException extends RuntimeException {
    private final String message;
}