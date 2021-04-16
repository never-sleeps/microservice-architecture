package com.microservicearchitecture.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CreateWithdrawRequest {
    private final BigDecimal amount;
}
