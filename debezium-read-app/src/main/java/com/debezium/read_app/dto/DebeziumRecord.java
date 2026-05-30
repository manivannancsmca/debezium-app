package com.debezium.read_app.dto;

public record DebeziumRecord(
    String before,
    AfterData after,
    SourceData source,
    String op,
    long ts_ms
) {
    public record AfterData(
        String event_id,
        String aggregate_id,
        String aggregate_type,
        String event_type,
        String payload
    ) {}
    
    public record SourceData(String db, String table) {}
}
