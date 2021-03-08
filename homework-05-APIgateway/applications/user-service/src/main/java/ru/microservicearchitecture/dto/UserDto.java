package ru.microservicearchitecture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.microservicearchitecture.model.User;

@Data
@AllArgsConstructor
public class UserDto {
    private String name;
    private String mail;

    public UserDto(User user) {
        this.name = user.getName();
        this.mail = user.getMail();
    }
}
