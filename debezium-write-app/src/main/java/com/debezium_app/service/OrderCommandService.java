package com.debezium_app.service;


import com.debezium_app.domain.OrderEntity;
import com.debezium_app.domain.OutboxEventEntity;
import com.debezium_app.dto.CreateOrderRequest;
import com.debezium_app.repository.OrderRepository;
import com.debezium_app.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OrderCommandService(OrderRepository orderRepository, OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UUID createOrder(CreateOrderRequest request) throws Exception {
        UUID orderId = UUID.randomUUID();
        
        OrderEntity order = new OrderEntity(orderId, request.customerId(), "CREATED", request.amount());
        
        orderRepository.save(order);

        UUID eventId = UUID.randomUUID();
        var payload = objectMapper.writeValueAsString(Map.of(
                "eventId", eventId.toString(),
                "orderId", orderId.toString(),
                "customerId", request.customerId().toString(),
                "amount", request.amount(),
                "status", "CREATED"
        ));

        OutboxEventEntity outbox = new OutboxEventEntity(
                eventId,
                "Order",
                orderId,
                "OrderCreated",
                payload
        );
        outboxEventRepository.save(outbox);

        return orderId;
    }
}