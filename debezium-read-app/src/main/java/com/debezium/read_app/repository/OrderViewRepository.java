package com.debezium.read_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.debezium.read_app.model.OrderViewEntity;

import java.util.UUID;

public interface OrderViewRepository extends JpaRepository<OrderViewEntity, UUID> {}
