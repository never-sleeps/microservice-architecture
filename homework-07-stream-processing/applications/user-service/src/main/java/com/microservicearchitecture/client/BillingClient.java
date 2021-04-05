package com.microservicearchitecture.client;

import com.microservicearchitecture.dto.request.CreateBillingAccountRequest;
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

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties("client.billing-service")
@Component
public class BillingClient {

    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public BillingClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void postConstruct() {
        log.info(String.format("Billing client: url '%s', token '%s'", url, token));
    }

    public void createBillingAccount(CreateBillingAccountRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<CreateBillingAccountRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);

        try {
            String host = String.format("%s/billing/accounts", url);
            ResponseEntity<?> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (!result.getStatusCode().equals(HttpStatus.CREATED)) {
                throw new IllegalStateException();
            }
        } catch (Exception e) {
            log.info("Ошибка создания счёта пользователя: {}", e.getLocalizedMessage());
            throw new IllegalStateException();
        }
        log.info("Создание счёта пользователя выполнено успешно");
    }
}
