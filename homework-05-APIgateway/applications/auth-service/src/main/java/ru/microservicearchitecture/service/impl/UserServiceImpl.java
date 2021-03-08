package ru.microservicearchitecture.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.microservicearchitecture.dto.UserDto;
import ru.microservicearchitecture.model.User;
import ru.microservicearchitecture.repository.UserRepository;
import ru.microservicearchitecture.service.SecurityService;
import ru.microservicearchitecture.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Override
    public User registerUser(UserDto userDto) {
        User newUser = User.builder()
                .login(userDto.getLogin())
                .password(securityService.encodePassword(userDto.getPassword()))
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login).orElse(null);
    }
}
