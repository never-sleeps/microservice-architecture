package ru.otus.microservicearchitecture.homework02.service;

import ru.otus.microservicearchitecture.homework02.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findAuthorById(long id);

    User createUser(User User);

    User updateUser(User user);

    void deleteUser(long id);
}
