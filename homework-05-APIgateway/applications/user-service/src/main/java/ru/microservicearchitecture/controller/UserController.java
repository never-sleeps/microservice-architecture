package ru.microservicearchitecture.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.microservicearchitecture.dto.ResponseDto;
import ru.microservicearchitecture.dto.UserDto;
import ru.microservicearchitecture.model.User;
import ru.microservicearchitecture.service.UserService;

@Slf4j
@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Пользователь получает данные о своем профиле
     * @param userId ID пользователя
     * @return объект ResponseDto с полями success и User
     */
    @GetMapping("/users/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getUserProfile(
            @RequestHeader("x-user-id") String userId
    ){
        log.info("Получение данных профиля: 'x-user-id'=" + userId);
        if (userId != null) {
            User user = userService.findById(Long.parseLong(userId));
            if (user != null) {
                return new ResponseDto(true, new UserDto(user));
            }
        }
        return new ResponseDto(false, null);
    }

    /**
     * Пользователь редактирует данные в своём профиле
     * @param userId ID пользователя
     * @param userDTO обновлённые данные профиля
     * @return ResponseEntity
     */
    @PostMapping("/users/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateUserProfile(
            @RequestHeader("x-user-id") String userId,
            @RequestBody UserDto userDTO
    ) {
        log.info("Обновление данных профиля 'x-user-id'=" + userId);
        userService.saveUser(Long.parseLong(userId), userDTO);
        return ResponseEntity.ok().build();
    }
}
