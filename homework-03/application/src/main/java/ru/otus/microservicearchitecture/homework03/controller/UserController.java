package ru.otus.microservicearchitecture.homework03.controller;

import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.microservicearchitecture.homework03.model.User;
import ru.otus.microservicearchitecture.homework03.repositories.UserRepository;

import java.util.List;

@Timed(value = "user.requests")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        if (Math.random() * 100 < 5){
            throw new IndexOutOfBoundsException("Just for fun. Need 5xx");
        }
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable("userId") Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable("userId") Long userId, @RequestBody User user){
        user.setId(userId);
        return userRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("userId") Long userId){
        userRepository.deleteById(userId);
    }
}
