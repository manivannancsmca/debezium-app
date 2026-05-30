package com.debezium.read_app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_view")
public class OrderViewEntity {

    @Id
    private UUID orderId;

    private UUID customerId;
    private String status;
    private BigDecimal amount;
    private UUID lastEventId;
    private Instant updatedAt;

    protected OrderViewEntity() {}

    public OrderViewEntity(UUID orderId, UUID customerId, String status, BigDecimal amount, UUID lastEventId, Instant updatedAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
        this.lastEventId = lastEventId;
        this.updatedAt = updatedAt;
    }

    public void update(UUID customerId, String status, BigDecimal amount, UUID eventId, Instant updatedAt) {
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
        this.lastEventId = eventId;
        this.updatedAt = updatedAt;
    }
}
