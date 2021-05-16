package com.microservicearchitecture.client.stock.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Временное решение. В дальнейшем будет передаваться список товаров с информацией об их количестве.
 * Сейчас необходима какая-то информация, на основе которой будет генериться ошибка в сервисе stock-service
 */
@Getter
@Builder
public class ReserveRequest {
    private BigDecimal price;
}
