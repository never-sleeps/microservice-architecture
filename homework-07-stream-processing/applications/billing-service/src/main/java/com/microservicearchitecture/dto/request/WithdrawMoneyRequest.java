package com.microservicearchitecture.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawMoneyRequest {
    private BigDecimal amount;
}
