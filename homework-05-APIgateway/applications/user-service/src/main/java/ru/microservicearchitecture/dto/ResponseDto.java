package ru.microservicearchitecture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto {
    private boolean success;
    private UserDto user;
}
