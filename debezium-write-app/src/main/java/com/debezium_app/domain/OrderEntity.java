package com.debezium_app.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID customerId;

    @Column(nullable = false, length = 32)
    private String status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    public void approve() {
        this.status = "APPROVED";
        this.updatedAt = Instant.now();
    }

    public OrderEntity(UUID id, UUID customerId, String status, BigDecimal amount) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

}