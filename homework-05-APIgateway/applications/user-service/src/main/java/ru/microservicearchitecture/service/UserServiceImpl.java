package ru.microservicearchitecture.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.microservicearchitecture.dto.UserDto;
import ru.microservicearchitecture.model.User;
import ru.microservicearchitecture.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(long userId, UserDto user) {
        User newUser = User.builder()
                .id(userId)
                .name(user.getName())
                .mail(user.getMail())
                .build();
        return userRepository.save(newUser);
    }
}
