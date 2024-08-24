package com.semihtumay.accountservice.service;

import com.semihtumay.accountservice.converter.AccountConverter;
import com.semihtumay.accountservice.converter.OutboxConverter;
import com.semihtumay.accountservice.dto.CreateAccountRequest;
import com.semihtumay.accountservice.model.Account;
import com.semihtumay.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final OutboxService outboxService;

    public AccountService(AccountRepository accountRepository, OutboxService outboxService) {
        this.accountRepository = accountRepository;
        this.outboxService = outboxService;
    }

    public void create(CreateAccountRequest request) {
        Account account = accountRepository.save(AccountConverter.accountConverterFromRequest(request));
        outboxService.create(OutboxConverter.convertToOutboxFromAccount(account));
    }
}
