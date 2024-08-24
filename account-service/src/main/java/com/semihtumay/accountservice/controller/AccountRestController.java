package com.semihtumay.accountservice.controller;

import com.semihtumay.accountservice.dto.CreateAccountRequest;
import com.semihtumay.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public void create(@RequestBody CreateAccountRequest request){
        accountService.create(request);
    }
}
