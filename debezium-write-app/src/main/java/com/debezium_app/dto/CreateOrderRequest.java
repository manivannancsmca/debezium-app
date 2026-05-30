package com.debezium_app.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(UUID customerId, BigDecimal amount) {} 