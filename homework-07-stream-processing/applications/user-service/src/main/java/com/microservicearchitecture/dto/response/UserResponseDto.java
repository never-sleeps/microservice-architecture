package com.microservicearchitecture.dto.response;

import com.microservicearchitecture.persistence.entity.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String mail;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.mail = user.getMail();
    }
}
