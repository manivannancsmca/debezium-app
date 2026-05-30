package com.debezium.read_app.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.debezium.read_app.dto.DebeziumRecord;
import com.debezium.read_app.events.OrderCreatedEvent;
import com.debezium.read_app.model.OrderViewEntity;
import com.debezium.read_app.model.ProcessedEventEntity;
import com.debezium.read_app.repository.OrderViewRepository;
import com.debezium.read_app.repository.ProcessedEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Service
@Slf4j
public class OrderConsumer {
    
    private final OrderViewRepository orderViewRepository;
    private final ProcessedEventRepository processedEventRepository;
    private final ObjectMapper objectMapper;

    
    public OrderConsumer(
            OrderViewRepository orderViewRepository,
            ProcessedEventRepository processedEventRepository,
            ObjectMapper objectMapper) {
        this.orderViewRepository = orderViewRepository;
        this.processedEventRepository = processedEventRepository;
        this.objectMapper = objectMapper;
    }
    
    @KafkaListener(topics = "outbox.event.Order", groupId = "read-model-consumer")
    @Transactional
    public void consume(String payload) {
        try {
            log.info("----------------");
            log.info("Received message: {}", payload);
            
            OrderCreatedEvent event = objectMapper.readValue(payload, OrderCreatedEvent.class);
            
            log.info("Processing event: {} for order: {}", event.eventId(), event.orderId());
            
            if (processedEventRepository.existsById(event.eventId())) {
                log.info("Event {} already processed, skipping", event.eventId());
                return;
            }
            
            OrderViewEntity view = orderViewRepository.findById(event.orderId())
                    .orElseGet(() -> new OrderViewEntity(
                            event.orderId(),
                            event.customerId(),
                            event.status(),
                            event.amount(),
                            event.eventId(),
                            Instant.now()
                    ));
            
            view.update(event.customerId(),
                event.status(), event.amount(), event.eventId(), Instant.now());
            
            orderViewRepository.save(view);
            processedEventRepository.save(new ProcessedEventEntity(event.eventId()));
            
            log.info("Successfully processed event: {}", event.eventId());
            
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON: {}", payload, e);
            throw new RuntimeException("Failed to process message", e);
        }
    }
}