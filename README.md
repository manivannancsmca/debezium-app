| Check             | Command                                                                        | Expected Result                                                 |
| ----------------- | ------------------------------------------------------------------------------ | --------------------------------------------------------------- |
| Connect API       | curl http://localhost:8083/connectors                                          | [] or list of connector names stackoverflow                     |
| Connector Plugins | curl http://localhost:8083/connector-plugins                                   | List including io.debezium.connector.mysql.MySqlConnector       |
| Connector Status  | curl http://localhost:8083/connectors/write-db-outbox-connector/status         | "state": "RUNNING"                                              |
| Kafka Topics      | docker compose exec kafka kafka-topics.sh --bootstrap-server kafka:9093 --list | Topics like write_db.outbox_event, connect_configs, etc. github |
