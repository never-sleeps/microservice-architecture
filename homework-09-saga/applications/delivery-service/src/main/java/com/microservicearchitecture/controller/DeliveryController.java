package com.microservicearchitecture.controller;

import com.microservicearchitecture.dto.request.DeliveryRequest;
import com.microservicearchitecture.exceptions.DeliveryException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    /**
     * Доставка заказа
     */
    @PostMapping("/send")
    public void reserve(@RequestBody DeliveryRequest request) {
        log.info("Доставка заказа");
        if (request.getAddress().contains("тест")) {
            throw new DeliveryException("Имитация ошибки при доставке (срабатывает, если адрес содержит 'тест')");
        }
    }
}
