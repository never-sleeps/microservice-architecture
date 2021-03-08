package ru.microservicearchitecture.service;

import ru.microservicearchitecture.dto.UserDto;
import ru.microservicearchitecture.model.User;

public interface UserService {

    User findById(long id);

    User saveUser(long userId, UserDto user);
}
