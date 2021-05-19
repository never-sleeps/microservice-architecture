package com.microservicearchitecture.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<Serializable, Object> redisTemplate;

    public void expire(final String key, Object value, Long expireTime) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error("Ошибка записи в Redis: " + ex.getLocalizedMessage());
        }
    }

    public boolean isExists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public boolean delete(final String key) {
        if (isExists(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }
}
