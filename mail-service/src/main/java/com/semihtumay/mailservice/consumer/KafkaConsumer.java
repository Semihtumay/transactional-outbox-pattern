package com.semihtumay.mailservice.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihtumay.mailservice.model.AccountEvent;
import com.semihtumay.mailservice.publisher.KafkaPublisher;
import com.semihtumay.mailservice.service.MailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumer {

    private final MailService mailService;
    private final KafkaPublisher kafkaPublisher;
    private static final String TOPIC_NAME = "account.public.outboxes";
    private static final String RETRY_TOPIC = "account.public.outbox.retry";
    private static final String ERROR_TOPIC = "account.public.outbox.error";
    private static final String GROUP_ID = "Mail-Service";
    private static final String CONTAINER_FACTORY = "ContainerFactory";
    private static final Map<String, Integer> retryCountsMap = new HashMap<>();
    private static final String RETRY_COUNT_KEY = "retryCount";

    private final ObjectMapper MAPPER = new ObjectMapper();

    public KafkaConsumer(MailService mailService, KafkaPublisher kafkaPublisher) {
        this.mailService = mailService;
        this.kafkaPublisher = kafkaPublisher;
    }

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID, containerFactory = CONTAINER_FACTORY)
    public void listener(ConsumerRecord<String, String> record) {
        handleEvent(record.value(), getRetryCount());
    }

    @KafkaListener(topics = RETRY_TOPIC, groupId = GROUP_ID, containerFactory = CONTAINER_FACTORY)
    public void retryListener(ConsumerRecord<String, String> record) {
        handleEvent(record.value(), getRetryCount());
    }

    @KafkaListener(topics = ERROR_TOPIC, groupId = GROUP_ID, containerFactory = CONTAINER_FACTORY)
    public void errorListener(ConsumerRecord<String, String> record) {
        String failedEvent = record.value();
        System.err.println("Received a message in Error Topic: " + failedEvent);
    }

    private void handleEvent(String value, Integer retryCount) {
        try {
            JsonNode eventNode = MAPPER.readTree(value);
            AccountEvent accountEvent = MAPPER.readValue(eventNode.get("payload").asText(), AccountEvent.class);
            mailService.sendMail(accountEvent.getUsername());
            mailService.deleteProcessByIdFromOutbox(accountEvent.getId());
        }catch (Exception e){
            if (retryCount < 3) {
                retryCountsMap.put(RETRY_COUNT_KEY, retryCount + 1);
                kafkaPublisher.publish(RETRY_TOPIC, value);
            } else {
                kafkaPublisher.publish(ERROR_TOPIC, value);
            }
        }
    }

    private Integer getRetryCount(){
        if (retryCountsMap.isEmpty()){
            retryCountsMap.put(RETRY_COUNT_KEY, 0);
            return 0;
        }else {
            return retryCountsMap.get(RETRY_COUNT_KEY);
        }
    }
}
