package com.microservicearchitecture.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Временное решение. В дальнейшем будет передаваться список товаров с информацией об их количестве.
 * Сейчас необходима какая-то информация, на основе которой будет генериться ошибка в сервисе stock-service
 */
@Getter
@Setter
public class ReserveRequest {
    private BigDecimal price;
}
