package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.response.ErrorResponse;
import com.microservicearchitecture.exceptions.AccountException;
import com.microservicearchitecture.exceptions.EntityNotFoundException;
import com.microservicearchitecture.exceptions.IdempotenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(EntityNotFoundException exception) {
        return buildError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleServiceException(AccountException exception) {
        return buildError(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(IdempotenceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleConflictException(IdempotenceException exception) {
        return buildError(HttpStatus.CONFLICT, exception);
    }

    private ErrorResponse buildError(HttpStatus status, Exception exception) {
        return ErrorResponse.builder()
                .code(status.value())
                .message(exception.getMessage())
                .build();
    }
}
