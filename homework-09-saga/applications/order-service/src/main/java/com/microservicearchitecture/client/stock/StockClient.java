package com.microservicearchitecture.client.stock;

import com.microservicearchitecture.client.billing.dto.CreateWithdrawRequest;
import com.microservicearchitecture.client.stock.dto.ReserveRequest;
import com.microservicearchitecture.client.stock.dto.UnreserveRequest;
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
@ConfigurationProperties("client.stock-service")
@Component
public class StockClient {

    private String url;
    private String token;

    private final RestTemplate restTemplate;

    @Autowired
    public StockClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct
    public void postConstruct() {
        log.info(String.format("Stock client Url: %s", url));
        log.info(String.format("Stock client Token: %s", token));
    }

    /**
     * Резервирование товаров на складе
     * @param request
     * @return true в случае успешного резервирования
     */
    public boolean reserve(ReserveRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<ReserveRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);
        try {
            String host = String.format("%s/stock/reserve", url);
            log.info("Sending create reserve request: {}", host);
            ResponseEntity<Void> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Create reserve success");
                return true;
            }
        } catch (Exception e) {
            log.error("Create reserve request failure: {}", e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Отмена резервирования товаров на складе
     * @param request
     */
    public boolean unreserve(UnreserveRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-service-token", token);
        HttpEntity<UnreserveRequest> requestHttpEntity = new HttpEntity<>(request, httpHeaders);
        try {
            String host = String.format("%s/stock/unreserve", url);
            log.info("Sending create reserve request: {}", host);
            ResponseEntity<Void> result = restTemplate.exchange(host, HttpMethod.POST, requestHttpEntity, Void.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                log.info("Create unreserve success");
                return true;
            }
        } catch (Exception e) {
            log.error("Create unreserve request failure: {}", e.getLocalizedMessage());
        }
        return false;
    }

}
