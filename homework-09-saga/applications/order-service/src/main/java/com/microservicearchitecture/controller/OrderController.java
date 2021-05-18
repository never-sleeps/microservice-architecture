package com.microservicearchitecture.controller;

import com.microservicearchitecture.config.annotations.IdempotenceKey;
import com.microservicearchitecture.dto.request.CreateOrderRequest;
import com.microservicearchitecture.dto.response.CreateOrderResponse;
import com.microservicearchitecture.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * Создание заказа
     * @param request данные заказа
     */
    @IdempotenceKey
    @PostMapping
    public CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    /**
     * Получение списка всех заказов
     * @return данные всех заказов
     */
    @GetMapping
    public List<CreateOrderResponse> findAllOrders() {
        return orderService.findAllOrders();
    }

    /**
     * Получение заказа по id
     * @id - id заказа
     * @return заказ
     */
    @GetMapping("/{id}")
    public CreateOrderResponse findOrderById(
            @PathVariable Long id
    ) {
        return orderService.findOrderById(id);
    }
}
