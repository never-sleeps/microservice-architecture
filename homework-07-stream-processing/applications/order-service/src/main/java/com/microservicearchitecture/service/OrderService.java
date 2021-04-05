package com.microservicearchitecture.service;

import com.microservicearchitecture.dto.request.CreateOrderRequest;

public interface OrderService {
    void createOrder(CreateOrderRequest request);
}
