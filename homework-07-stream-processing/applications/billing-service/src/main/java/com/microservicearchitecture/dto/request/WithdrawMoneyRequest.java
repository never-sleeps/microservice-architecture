package com.microservicearchitecture.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WithdrawMoneyRequest {
    private BigDecimal amount;
}
