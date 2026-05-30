package com.debezium_app.dto;

import java.util.UUID;

public record OrderResponse(UUID orderId, String status) {}
