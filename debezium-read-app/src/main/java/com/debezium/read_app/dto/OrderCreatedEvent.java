package com.debezium.read_app.dto;

import java.math.BigDecimal;

public record OrderCreatedEvent(
    String eventId,
    String orderId,
    String customerId,
    String status,
    BigDecimal amount
) {}
