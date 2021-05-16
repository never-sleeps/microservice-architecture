package com.microservicearchitecture.client.delivery.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Временное решение. В дальнейшем будет передаваться данные о заказе.
 * Сейчас необходима какая-то информация, на основе которой будет генериться ошибка в сервисе delivery-service
 */
@Getter
@Builder
public class DeliveryRequest {
    private String address;
}
