package com.microservicearchitecture.dto.response;

import com.microservicearchitecture.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class GetUserResponse {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;

    public GetUserResponse(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
