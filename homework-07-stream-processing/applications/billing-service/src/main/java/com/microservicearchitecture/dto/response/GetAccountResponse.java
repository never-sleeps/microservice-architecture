package com.microservicearchitecture.dto.response;

import com.microservicearchitecture.persistence.entity.Account;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GetAccountResponse {
    private final Long userId;
    private final String mail;
    private final BigDecimal balance;

    public GetAccountResponse(Account entity) {
        this.userId = entity.getUserId();
        this.mail = entity.getMail();
        this.balance = entity.getBalance();
    }
}
