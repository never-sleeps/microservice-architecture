package ru.microservicearchitecture.service;

import ru.microservicearchitecture.dto.UserDto;
import ru.microservicearchitecture.model.User;

public interface UserService {

    User registerUser(UserDto userDto);

    User findUserByLogin(String login);
}
