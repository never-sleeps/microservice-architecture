package com.microservicearchitecture.client;

import com.microservicearchitecture.dto.request.CreateWithdrawRequest;
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
        log.info(String.format("Billing client Url: %s", url));
        log.info(String.format("Billing client Token: %s", token));
    }

    public boolean withdrawMoney(Long userId, CreateWithdrawRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<CreateWithdrawRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);
        try {
            String host = String.format("%s/billing/accounts/%s/withdraw", url, userId);
            log.info("Sending create withdraw request: {}", host);
            ResponseEntity<Void> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Create withdraw success");
                return true;
            }
        } catch (Exception e) {
            log.info("Create withdraw request by userId failure: {}", e.getLocalizedMessage());
        }
        log.info("Insufficient amount status");
        return false;
    }
}
