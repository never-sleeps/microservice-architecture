package ru.microservicearchitecture.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.microservicearchitecture.dto.UserDto;
import ru.microservicearchitecture.model.Session;
import ru.microservicearchitecture.model.User;
import ru.microservicearchitecture.service.SecurityService;
import ru.microservicearchitecture.service.SessionService;
import ru.microservicearchitecture.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;
    private final SecurityService securityService;
    public static final String AUTH_COOKIE_NAME = "arch_homework_auth_cookie";

    /**
     * Пользователь регистрируется в приложении
     * @param userDto данные пользователя
     * @return ResponseEntity
     */
    @PostMapping("/registration")
    private ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userService.findUserByLogin(userDto.getLogin()) != null) {
            log.warn("Пользователь с логином '" + userDto.getLogin() + "' не найден");
            ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        User user = userService.registerUser(userDto);
        log.info("Пользователь '" + user.toString() + "' зарегистрирован");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Пользователь авторизовывается
     * @param userDto данные пользователя
     * @param httpServletResponse HttpServletResponse
     * @return ResponseEntity
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody UserDto userDto,
            HttpServletResponse httpServletResponse
    ) {
        User user = userService.findUserByLogin(userDto.getLogin());
        // Проверяем наличие пользователя с таким логином
        if (user == null) {
            log.warn("Пользователь с логином '" + userDto.getLogin() + "' не найден");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Проверяем, что у найденного пользователя такой же пароль
        if (!securityService.encodePassword(userDto.getPassword()).equals(user.getPassword())) {
            log.warn("Пользователь '" + userDto.getLogin() + "': пароль неверный");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // Сохраняем сессию для пользователя
        Session session = sessionService.saveSessionForUser(user);
        log.warn("Пользователь '" + userDto.getLogin() + "' авторизован");
        // Добавляем куку с сессией
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, session.getId().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Пользователь выходит из системы
     * @param sessionId сессия
     * @param response HttpServletResponse
     * @return ResponseEntity
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(
            @CookieValue(name = AUTH_COOKIE_NAME, required = false) String sessionId,
            HttpServletResponse response
    ) {
        // Проверяем, что кука сессии не равно null
        if (sessionId == null) {
            log.warn("Кука сессии = null");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // Проверяем, что сессия с такой кукой существует
        if (sessionService.findSession(sessionId) == null) {
            log.warn("Кука '" + sessionId + "' не найдена");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // Удаляем сессию
        sessionService.deleteSession(sessionId);
        log.warn("Сессия '" + sessionId + "' удалена из бд");
        // Добавляем пустую куку с сессией
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/auth")
    public ResponseEntity<?> auth(
            @CookieValue(name = AUTH_COOKIE_NAME, required = false) String sessionId,
            HttpServletResponse response
    ) {
        // Проверяем, что кука сессии не равно null
        if (sessionId == null) {
            log.warn("Кука сессии = null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Проверяем, что сессия с такой кукой существует
        Session session = sessionService.findSession(sessionId);
        if (session == null) {
            log.warn("Кука '" + sessionId + "' не найдена");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // добавляем userId в параметр x-user-id
        response.addHeader("x-user-id", String.valueOf(session.getUserId()));
        log.info("Параметр 'x-user-id'='" + session.getUserId() + "' был добавлен для сессии '" + sessionId + "'");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
