package com.microservicearchitecture.client;

import com.microservicearchitecture.dto.response.UserDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Data
@Configuration
@ConfigurationProperties("client.user-service")
@Component
public class UserClient {
    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public UserClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Optional<UserDto> findUserById(Long userId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<?> requestHttpEntity = new HttpEntity<>(null, httpHeaders);

        try {
            String host = String.format("%s/users/%s", url, userId);
            log.info("Получение данных пользователя userId=" + userId + ": {}", host);
            ResponseEntity<UserDto> result = restTemplate.exchange(
                    host, HttpMethod.GET, requestHttpEntity, UserDto.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Данные пользователя userId=" + userId + "получены успешно");
                return Optional.ofNullable(result.getBody());
            }
        } catch (Exception e) {
            log.info("Ошибка получения данных пользователя userId=" + userId + ": {}", e.getLocalizedMessage());
        }
        return Optional.empty();
    }
}
