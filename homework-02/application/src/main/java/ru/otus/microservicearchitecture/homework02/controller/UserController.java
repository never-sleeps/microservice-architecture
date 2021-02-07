package ru.otus.microservicearchitecture.homework02.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.microservicearchitecture.homework02.model.User;
import ru.otus.microservicearchitecture.homework02.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    private List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Object getUser(@PathVariable("id") long id) {
        return userService.findAuthorById(id);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    private User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    private User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}
