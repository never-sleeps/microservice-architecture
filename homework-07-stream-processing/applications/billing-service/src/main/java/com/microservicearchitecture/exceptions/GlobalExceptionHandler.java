package com.microservicearchitecture.exceptions;

import com.microservicearchitecture.dto.response.ErrorResponseDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDto handleNotFoundException(EntityNotFoundException exception) {
        return buildError(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(AccountBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto handleServiceException(AccountBalanceException exception) {
        return buildError(HttpStatus.BAD_REQUEST, exception);
    }

    private ErrorResponseDto buildError(HttpStatus status, Exception exception) {
        return ErrorResponseDto.builder()
                .code(status.value())
                .message(exception.getMessage())
                .build();
    }
}
