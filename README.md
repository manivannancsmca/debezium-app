| Check             | Command                                                                        | Expected Result                                                 |
| ----------------- | ------------------------------------------------------------------------------ | --------------------------------------------------------------- |
| Connect API       | curl http://localhost:8083/connectors                                          | [] or list of connector names stackoverflow                     |
| Connector Plugins | curl http://localhost:8083/connector-plugins                                   | List including io.debezium.connector.mysql.MySqlConnector       |
| Connector Status  | curl http://localhost:8083/connectors/write-db-outbox-connector/status         | "state": "RUNNING"                                              |
| Kafka Topics      | docker compose exec kafka kafka-topics.sh --bootstrap-server kafka:9093 --list | Topics like write_db.outbox_event, connect_configs, etc. github |


# The Fix: Create the Connector

## Step 1: Verify Connect is Ready

```powershell
curl http://localhost:8083/connectors
```

**Expected Output**

```json
[]
```

This confirms Kafka Connect is running and currently has no connectors configured.

---

## Step 2: Create the Connector (PowerShell)

```powershell
# Read the JSON file and create connector
$body = Get-Content write-db-outbox-connector.json -Raw -Encoding UTF8

$response = Invoke-WebRequest `
  -Uri "http://localhost:8083/connectors" `
  -Method POST `
  -ContentType "application/json" `
  -Body $body

# Show response
$response.Content | ConvertFrom-Json | ConvertTo-Json -Depth 15
```

### Alternative: Using Curl

```powershell
curl -X POST http://localhost:8083/connectors `
  -H "Content-Type: application/json" `
  -Body (Get-Content write-db-outbox-connector.json -Raw -Encoding UTF8)
```

---

## Step 3: Check Response for Errors

| Response          | Meaning                                                    |
| ----------------- | ---------------------------------------------------------- |
| `409 Conflict`    | Connector already exists (delete it first).                |
| `400 Bad Request` | Invalid connector configuration. Review the error message. |
| `200/201 Success` | Connector created successfully.                            |

**Successful Response Example**

```json
{
  "name": "write-db-outbox-connector"
}
```

---

## Step 4: If Connector Already Exists, Delete and Recreate

### Delete Existing Connector

```powershell
curl -X DELETE http://localhost:8083/connectors/write-db-outbox-connector
```

### Wait for Cleanup

```powershell
Start-Sleep -Seconds 5
```

### Recreate Connector

```powershell
$body = Get-Content write-db-outbox-connector.json -Raw -Encoding UTF8

Invoke-WebRequest `
  -Uri "http://localhost:8083/connectors" `
  -Method POST `
  -ContentType "application/json" `
  -Body $body
```

---

## Step 5: Verify Connector Status

### Wait for Connector Initialization

```powershell
Start-Sleep -Seconds 15
```

### Check Status

```powershell
curl http://localhost:8083/connectors/write-db-outbox-connector/status
```

### Expected Success Response

```json
{
  "connector": {
    "name": "write-db-outbox-connector",
    "state": "RUNNING",
    "worker_id": "172.21.0.4:8083"
  },
  "task": {
    "id": 0,
    "state": "RUNNING"
  }
}
```

### Validation Checklist

* Connector state is `RUNNING`
* Task state is `RUNNING`
* No error messages in Kafka Connect logs
* Connector is visible in the connector list:

```powershell
curl http://localhost:8083/connectors
```

Expected output:

```json
[
  "write-db-outbox-connector"
]
```
