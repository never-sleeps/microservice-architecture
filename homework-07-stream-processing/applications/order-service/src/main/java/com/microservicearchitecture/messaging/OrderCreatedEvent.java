package com.microservicearchitecture.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedEvent implements DomainEvent {
    @JsonProperty("userId")
    private final Long userId;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("orderId")
    private final Long orderId;

    @JsonProperty("status")
    private final String status;
}
