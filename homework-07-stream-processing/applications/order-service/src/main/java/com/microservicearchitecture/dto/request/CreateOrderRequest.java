package com.microservicearchitecture.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateOrderRequest {
    private BigDecimal price;
    private Long userId;
}
