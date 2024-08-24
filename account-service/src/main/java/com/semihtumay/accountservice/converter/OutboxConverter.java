package com.semihtumay.accountservice.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihtumay.accountservice.model.Account;
import com.semihtumay.accountservice.model.Outbox;
import com.semihtumay.accountservice.model.enums.OutboxType;

public class OutboxConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static Outbox convertToOutboxFromAccount(Account account){
        try {
            String payload = MAPPER.writeValueAsString(account);
            Outbox outbox = new Outbox();
            outbox.setAggregateType("account");
            outbox.setAggregateId(account.getId().toString());
            outbox.setType("AccountCreated");
            outbox.setPayload(payload);
            return outbox;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
