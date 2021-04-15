package com.microservicearchitecture.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderRequest {
    private BigDecimal price;
    private Long userId;
}
