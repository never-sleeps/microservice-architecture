package com.microservicearchitecture.client;

import com.microservicearchitecture.dto.request.CreateWithdrawRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Data
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

    /**
     * Оплата заказа пользователя
     * @param userId id пользователя
     * @param request данные снятия со счёта
     * @return true в случае успешной оплаты
     */
    public boolean withdrawMoney(Long userId, CreateWithdrawRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<CreateWithdrawRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);

        try {
            String host = String.format("%s/billing/accounts/%s/withdraw", url, userId);
            ResponseEntity<Void> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Оплата для userId=" + userId + " прошла успешно");
                return true;
            }
        } catch (Exception e) {
            log.info("Ошибка оплаты для userId=" + userId + ": {}", e.getLocalizedMessage());
        }
        return false;
    }
}
