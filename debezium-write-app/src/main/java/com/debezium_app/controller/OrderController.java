package com.debezium_app.controller;

import org.springframework.web.bind.annotation.*;

import com.debezium_app.dto.CreateOrderRequest;
import com.debezium_app.dto.OrderResponse;
import com.debezium_app.service.OrderCommandService;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderCommandService service;

    public OrderController(OrderCommandService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponse create(@RequestBody CreateOrderRequest request) throws Exception {
        UUID orderId = service.createOrder(request);
        return new OrderResponse(orderId, "CREATED");
    }
}
