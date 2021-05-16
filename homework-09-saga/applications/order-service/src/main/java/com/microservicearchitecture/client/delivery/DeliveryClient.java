package com.microservicearchitecture.client.delivery;

import com.microservicearchitecture.client.delivery.dto.DeliveryRequest;
import com.microservicearchitecture.client.stock.dto.ReserveRequest;
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
@ConfigurationProperties("client.delivery-service")
@Component
public class DeliveryClient {

    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public DeliveryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void postConstruct() {
        log.info(String.format("Delivery client Url: %s", url));
        log.info(String.format("Delivery client Token: %s", token));
    }

    /**
     * Передача товара в доставку
     * @param request
     * @return true в случае успешной доставки
     */
    public boolean delivery(DeliveryRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<DeliveryRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);
        try {
            String host = String.format("%s/delivery/delivery/send", url);
            log.info("Sending create delivery request: {}", host);
            ResponseEntity<Void> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Create delivery success");
                return true;
            }
        } catch (Exception e) {
            log.error("Create delivery request failure: {}", e.getLocalizedMessage());
        }
        return false;
    }
}
