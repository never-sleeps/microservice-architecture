package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.request.CreateUserRequest;
import com.microservicearchitecture.dto.request.UpdateUserRequest;
import com.microservicearchitecture.dto.response.GetUserResponse;
import com.microservicearchitecture.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Получение данных всех пользователей
     * @return данные всех пользователей
     */
    @GetMapping
    public List<GetUserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Получение данных пользователя по id
     * @param id - id пользователя
     * @return данные пользователя
     */
    @GetMapping("/{id}")
    public GetUserResponse findUserById(
            @PathVariable long id
    ) {
        return userService.findUserById(id);
    }

    /**
     * Создание нового пользователя
     * @param request данные пользователя
     * @return данные созданного пользователя
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetUserResponse createUser(
            @RequestBody @Validated CreateUserRequest request
    ) {
        return userService.createUser(request);
    }

    /**
     * Обновление данные пользователя
     * @param id - id пользователя
     * @param request новые данные пользователя
     * @return данные обновленного пользователя
     */
    @PutMapping("/{id}")
    public GetUserResponse updateUserById(
            @PathVariable long id,
            @RequestBody @Validated UpdateUserRequest request
    ) {
        return userService.updateUserById(id, request);
    }

    /**
     * Удаление пользователя по id
     * @param id - id пользователя
     */
    @DeleteMapping("/{id}")
    public void deleteUserById(
            @PathVariable long id
    ) {
        userService.deleteUserById(id);
    }
}
