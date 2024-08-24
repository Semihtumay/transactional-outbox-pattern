package com.semihtumay.accountservice.consumer;

import com.semihtumay.accountservice.service.OutboxService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaConsumer {
    private static final String TOPIC_NAME = "delete-process-byId-from-outbox";
    private static final String GROUP_ID = "Account-Service";
    private static final String CONTAINER_FACTORY = "ContainerFactory";

    private final OutboxService outboxService;

    public KafkaConsumer(OutboxService outboxService) {
        this.outboxService = outboxService;
    }

    @KafkaListener(topics = {TOPIC_NAME}, groupId = GROUP_ID, containerFactory = CONTAINER_FACTORY)
    public void consumeMessage(UUID message) {
        outboxService.deleteById(message);
    }
}
