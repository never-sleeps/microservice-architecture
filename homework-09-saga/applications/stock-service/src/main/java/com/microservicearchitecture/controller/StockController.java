package com.microservicearchitecture.controller;


import com.microservicearchitecture.dto.request.ReserveRequest;
import com.microservicearchitecture.dto.request.UnreserveRequest;
import com.microservicearchitecture.exceptions.StockException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/goods")
public class StockController {

    /**
     * Резервирование товаров
     */
    @GetMapping("/reserve")
    public void reserve(@RequestBody ReserveRequest request) {
        log.info("Резервирование товаров");
        if (request.getPrice().compareTo(BigDecimal.valueOf(500)) == 0) {
            throw new StockException("Имитация ошибки при бронировании товара (срабатывает, если price = 500");
        }
    }

    /**
     * Отмена резервирования товаров
     */
    @GetMapping("/unreserve")
    public void unreserve(@RequestBody UnreserveRequest request) {
        log.info("Отмена резервирования товаров");
    }

}
