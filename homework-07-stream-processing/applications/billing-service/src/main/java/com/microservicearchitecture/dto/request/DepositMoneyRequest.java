package com.microservicearchitecture.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DepositMoneyRequest {
    private BigDecimal amount;
}
