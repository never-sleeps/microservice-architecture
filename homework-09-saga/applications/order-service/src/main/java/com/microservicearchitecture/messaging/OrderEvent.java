package com.microservicearchitecture.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microservicearchitecture.enumerations.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderEvent implements DomainEvent {
    @JsonProperty("userId")
    private final Long userId;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("orderId")
    private final Long orderId;

    @JsonProperty("status")
    private final NotificationType status;
}
