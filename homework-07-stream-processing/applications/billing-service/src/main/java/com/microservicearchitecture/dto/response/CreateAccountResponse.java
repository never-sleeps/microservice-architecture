package com.microservicearchitecture.dto.response;

import com.microservicearchitecture.persistence.entity.Account;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateAccountResponse {
    private final Long id;
    private String mail;
    private Long userId;

    public CreateAccountResponse(Account account) {
        this.id = account.getId();
        this.mail = account.getMail();
        this.userId = account.getUserId();
    }
}
