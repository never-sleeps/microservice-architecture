package com.microservicearchitecture.controller;

import com.microservicearchitecture.exceptions.ResponseErrorDto;
import com.microservicearchitecture.exceptions.StockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus
    @ResponseBody
    public ResponseErrorDto handleStockException(StockException exception) {
        return ResponseErrorDto.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
    }
}
