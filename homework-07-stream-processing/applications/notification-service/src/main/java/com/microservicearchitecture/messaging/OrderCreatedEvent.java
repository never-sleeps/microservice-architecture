package com.microservicearchitecture.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements DomainEvent {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("mail")
    private String mail;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("status")
    private String status;
}
