package com.debezium.read_app.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "processed_event")
public class ProcessedEventEntity {

    @Id
    private UUID eventId;

    private Instant processedAt;

    protected ProcessedEventEntity() {}

    public ProcessedEventEntity(UUID eventId) {
        this.eventId = eventId;
        this.processedAt = Instant.now();
    }
}
