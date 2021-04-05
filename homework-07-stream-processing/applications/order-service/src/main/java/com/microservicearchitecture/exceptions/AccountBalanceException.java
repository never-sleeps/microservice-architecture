package com.microservicearchitecture.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountBalanceException extends RuntimeException {
    private final String message;
}