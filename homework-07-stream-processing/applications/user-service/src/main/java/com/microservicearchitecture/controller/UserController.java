package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.request.UserRequestDto;
import com.microservicearchitecture.dto.response.UserResponseDto;
import com.microservicearchitecture.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Получение списка пользователей
     * @return список пользователей
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> findAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Получение данных пользователя по его id
     * @param id пользователя
     * @return данные пользователя
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    /**
     * Создание пользователя
     * @param request данные пользователя
     * @return данные созданного пользователя
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto request) {
        return userService.createUser(request);
    }

    /**
     * Обновление данных пользователя по id
     * @param id пользователя
     * @param request новые данные пользователя
     * @return данные изменённого пользователя
     */
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponseDto updateUserById(@PathVariable Long id, @RequestBody UserRequestDto request) {
        return userService.updateUserById(id, request);
    }

    /**
     * Удаление пользователя по id
     * @param id пользователя
     */
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
