package com.debezium_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.debezium_app.domain.OutboxEventEntity;

import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {}
