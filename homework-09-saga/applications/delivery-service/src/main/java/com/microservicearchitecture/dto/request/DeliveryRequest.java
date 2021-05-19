package com.microservicearchitecture.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Временное решение. В дальнейшем будет передаваться данные о заказе.
 * Сейчас необходима какая-то информация, на основе которой будет генериться ошибка в сервисе delivery-service
 */
@Getter
@Setter
public class DeliveryRequest {
    private String address;
}
