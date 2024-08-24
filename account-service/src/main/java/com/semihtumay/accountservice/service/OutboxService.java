package com.semihtumay.accountservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihtumay.accountservice.model.Outbox;
import com.semihtumay.accountservice.publisher.KafkaPublisher;
import com.semihtumay.accountservice.repository.OutboxRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OutboxService {
    private final OutboxRepository outboxRepository;

    public OutboxService(OutboxRepository outboxRepository) {
        this.outboxRepository = outboxRepository;
    }

    public void create(Outbox outbox){
        outboxRepository.save(outbox);
    }

    public void deleteById(UUID id) {
        outboxRepository.deleteById(findMatchingIdInPayload(id));
    }

    public UUID findMatchingIdInPayload(UUID targetId) {
        List<Outbox> outboxList = outboxRepository.findAll();

        for (Outbox outbox : outboxList) {
            String payload = outbox.getPayload();
            try {
                JsonNode jsonNode = new ObjectMapper().readTree(payload);
                String payloadId = jsonNode.get("id").asText();

                if (targetId.toString().equals(payloadId)) {
                    return outbox.getId();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
