package com.microservicearchitecture.client.user;

import com.microservicearchitecture.client.user.dto.UserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Getter
@Setter
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

    @PostConstruct
    public void postConstruct() {
        log.info(String.format("User client Url: %s", url));
        log.info(String.format("User client Token: %s", token));
    }

    public Optional<UserResponse> findUserById(Long userId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<?> requestHttpEntity = new HttpEntity<>(null, httpHeaders);
        try {
            String host = String.format("%s/users/%s", url, userId);
            log.info("Sending find user request: {}", host);
            ResponseEntity<UserResponse> result = restTemplate
                    .exchange(host, HttpMethod.GET, requestHttpEntity, UserResponse.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("User found success");
                return Optional.ofNullable(result.getBody());
            }
        } catch (Exception e) {
            log.info("User finding by id failure: {}", e.getLocalizedMessage());
        }
        log.info("User finding failure");
        return Optional.empty();
    }
}
