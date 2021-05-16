package com.microservicearchitecture.dto.response;

import com.microservicearchitecture.enumerations.OrderStatus;
import com.microservicearchitecture.persistence.entity.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderResponse {
    private Long id;
    private BigDecimal price;
    private Long userId;
    private OrderStatus status;

    public CreateOrderResponse(OrderEntity entity) {
        this.id = entity.getId();
        this.price = entity.getPrice();
        this.userId = entity.getUserId();
        this.status = entity.getStatus();
    }
}
