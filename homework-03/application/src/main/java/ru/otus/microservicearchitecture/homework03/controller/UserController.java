package ru.otus.microservicearchitecture.homework03.controller;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.microservicearchitecture.homework03.model.User;
import ru.otus.microservicearchitecture.homework03.service.UserService;

import java.util.List;

@Timed(
        value = "users-service.timed.http.request",
        percentiles = {0.5, 0.95, 0.99, 1},
        histogram = true
)
@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    private List<User> getAll() {
        if (Math.random() * 10 < 2){
            throw new IndexOutOfBoundsException("Just for fun. 20% of 5xx");
        }
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
