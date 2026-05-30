package com.debezium.read_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.debezium.read_app.model.ProcessedEventEntity;

import java.util.UUID;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, UUID> {}
