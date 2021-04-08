package com.microservicearchitecture.dto.response;

import com.microservicearchitecture.persistence.entity.BillingAccountEntity;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GetAccountResponse {
    private final Long id;
    private final Long userId;
    private final String email;
    private final BigDecimal balance;

    public GetAccountResponse(BillingAccountEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.email = entity.getEmail();
        this.balance = entity.getBalance();
    }
}
