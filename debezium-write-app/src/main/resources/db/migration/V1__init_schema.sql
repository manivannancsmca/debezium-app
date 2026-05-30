CREATE DATABASE IF NOT EXISTS write_db;
USE write_db;

SET PERSIST binlog_format = 'ROW';
SET PERSIST binlog_row_image = 'FULL';

CREATE USER 'debezium'@'%' IDENTIFIED BY 'debezium';
GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'debezium'@'%';
FLUSH PRIVILEGES;


CREATE TABLE orders (
    id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    status VARCHAR(32) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);

CREATE TABLE outbox_event (
    event_id BINARY(16) PRIMARY KEY,
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id BINARY(16) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload JSON NOT NULL,
    headers JSON NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    published_at TIMESTAMP(6) NULL,
    UNIQUE KEY uk_outbox_dedup (aggregate_id, event_type, created_at)
);