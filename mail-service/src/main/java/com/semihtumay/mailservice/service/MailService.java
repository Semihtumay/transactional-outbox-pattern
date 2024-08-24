package com.semihtumay.mailservice.service;

import com.semihtumay.mailservice.publisher.KafkaPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailService {

    private final KafkaPublisher kafkaPublisher;

    public MailService(KafkaPublisher kafkaPublisher) {
        this.kafkaPublisher = kafkaPublisher;
    }

    public void sendMail(String username){
        if (username != null){
            System.out.println("Welcome " + username);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public void deleteProcessByIdFromOutbox(UUID id) {
        kafkaPublisher.publish("delete-process-byId-from-outbox", id);
    }
}
