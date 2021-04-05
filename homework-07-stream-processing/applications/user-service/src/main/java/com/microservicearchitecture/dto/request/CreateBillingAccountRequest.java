package com.microservicearchitecture.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBillingAccountRequest {
    private final Long userId;
    private final String mail;
}
