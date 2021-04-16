package com.microservicearchitecture.service;

import com.microservicearchitecture.exceptions.IdempotenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class IdempotenceKeyService {

    private final RedisService redisService;

    /**
     * Генерируем Idempotence Key и сохраняем его в Redis
     * @return Idempotence Key
     */
    public String generateIdempotenceKey() {
        String token = null;
        try {
            token = UUID.randomUUID().toString();
            redisService.expire(token, token, 1000L);
            return token;
        } catch (Exception e) {
            log.error("Exception: " + e.getLocalizedMessage());
        }
        return token;
    }

    /**
     * Проверяем наличие x-request-id в запросе. Если он null или пустой, кидаем ошибку.
     * Проверяем наличие ключа в Redis'е (чтобы исключить использование сторонних ключей). Если его нет, кидаем ошибку.
     *
     * @param request - запрос
     * @return true - если запрос идемпотентный
     */
    public boolean checkIdempotenceRequest(HttpServletRequest request) {
        String token = request.getHeader("x-request-id");
        if (token == null) {
            throw new IdempotenceException("idempotence key missing");
        }

        if (!redisService.isExists(token)) {
            throw new IdempotenceException("idempotence key missing");
        }

        if (!redisService.delete(token)) {
            throw new IdempotenceException("duplicated request");
        }

        return true;
    }
}
