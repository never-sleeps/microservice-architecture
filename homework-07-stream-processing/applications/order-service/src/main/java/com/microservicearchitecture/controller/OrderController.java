package com.microservicearchitecture.controller;

import com.microservicearchitecture.service.OrderService;
import com.microservicearchitecture.dto.request.CreateOrderRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Создание заказа
     * @param request данные заказа
     */
    @PostMapping
    public void createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Создание заказа для пользователя userId=" + request.getUserId());
        orderService.createOrder(request);
    }
}
