package com.debezium_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.debezium_app.domain.OrderEntity;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {}
