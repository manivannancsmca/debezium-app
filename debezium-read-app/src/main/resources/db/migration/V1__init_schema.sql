CREATE DATABASE IF NOT EXISTS read_db;
USE read_db;

CREATE TABLE order_view (
    order_id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    status VARCHAR(32) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    last_event_id BINARY(16) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE processed_event (
    event_id BINARY(16) PRIMARY KEY,
    processed_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
);