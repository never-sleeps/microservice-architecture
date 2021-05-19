package com.microservicearchitecture.controller;

import com.microservicearchitecture.service.IdempotenceKeyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idempotence-key")
@AllArgsConstructor
public class IdempotenceKeyGeneratorController {

    private final IdempotenceKeyService idempotenceKeyService;

    /**
     * Генерация x-request-id для последующего его использования при создании заказа.
     *
     * Предполагается генерация этого значение на клиенте, но, поскольку проект учебный
     * и в качестве клиента будет использоваться postman, было принято генерировать значение в сервисе
     */
    @PostMapping("/generate")
    public ResponseEntity<?> generateIdempotenceKey() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("x-request-id", idempotenceKeyService.generateIdempotenceKey());
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }
}
