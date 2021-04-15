package com.microservicearchitecture.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAccountResponse {
    private final Long id;
    private String email;
    private Long userId;
}
