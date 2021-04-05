package com.microservicearchitecture.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedEvent {
    @JsonProperty("userId")
    private final Long userId;

    @JsonProperty("mail")
    private final String mail;

    @JsonProperty("orderId")
    private final Long orderId;

    @JsonProperty("status")
    private final String status;
}
