curl -i -X POST -H "Accept:application/json" \
    -H  "Content-Type:application/json" http://localhost:8083/connectors/ \
    -d '{
          "name": "debezium-postgres-source-connector",
            "config": {
              "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
              "tasks.max": "1",
              "database.hostname": "db",
              "database.port": "5432",
              "database.user": "postgres",
              "database.password": "210821",
              "database.dbname": "outbox",
              "database.server.name": "outbox_server",
              "table.include.list": "public.outboxes",
              "plugin.name": "pgoutput",
              "snapshot.mode": "initial",
              "topic.prefix": "dbserver1",
              "transforms": "outbox",
              "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
              "transforms.outbox.route.topic.replacement": "account.public.outboxes"
            }
        }'
