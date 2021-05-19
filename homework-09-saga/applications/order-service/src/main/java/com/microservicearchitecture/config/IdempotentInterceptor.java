package com.microservicearchitecture.config;

import com.microservicearchitecture.config.annotations.IdempotenceKey;
import com.microservicearchitecture.exceptions.IdempotenceException;
import com.microservicearchitecture.service.IdempotenceKeyService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    private final IdempotenceKeyService idempotenceKeyService;

    public IdempotentInterceptor(IdempotenceKeyService idempotenceKeyService) {
        this.idempotenceKeyService = idempotenceKeyService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (!(handler instanceof HandlerMethod)) { return true; }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        IdempotenceKey methodAnnotation = method.getAnnotation(IdempotenceKey.class);
        if (methodAnnotation != null) {
            try {
                return idempotenceKeyService.checkIdempotenceRequest(request);
            } catch (IdempotenceException e) {
                throw e;
            } catch (Exception e) {
                throw new IdempotenceException("check idempotence exception:" + e.getMessage());
            }
        }
        return true;
    }
}
