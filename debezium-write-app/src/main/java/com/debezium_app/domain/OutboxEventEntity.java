package com.debezium_app.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_event")
@Data
@ToString
public class OutboxEventEntity {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID eventId;

    @Column(nullable = false, length = 100)
    private String aggregateType;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID aggregateId;

    @Column(nullable = false, length = 100)
    private String eventType;

    @Column(nullable = false, columnDefinition = "json")
    private String payload;

    @Column(columnDefinition = "json")
    private String headers;

    @Column(nullable = false)
    private Instant createdAt;

    protected OutboxEventEntity() {}

    public OutboxEventEntity(UUID eventId, String aggregateType, UUID aggregateId, String eventType, String payload) {
        this.eventId = eventId;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.createdAt = Instant.now();
    }

}