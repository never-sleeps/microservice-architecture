package com.microservicearchitecture.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAccountRequest {
    @NotBlank
    private String email;
    @NotNull
    private Long userId;
}
