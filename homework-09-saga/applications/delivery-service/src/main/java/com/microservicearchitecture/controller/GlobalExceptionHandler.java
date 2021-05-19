package com.microservicearchitecture.controller;

import com.microservicearchitecture.exceptions.ResponseErrorDto;
import com.microservicearchitecture.exceptions.DeliveryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus
    @ResponseBody
    public ResponseErrorDto handleDeliveryException(DeliveryException exception) {
        return ResponseErrorDto.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
    }
}
