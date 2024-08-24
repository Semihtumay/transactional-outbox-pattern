package com.semihtumay.accountservice.converter;

import com.semihtumay.accountservice.dto.CreateAccountRequest;
import com.semihtumay.accountservice.model.Account;
import com.semihtumay.accountservice.model.enums.MailStatus;

public class AccountConverter {
    public static Account accountConverterFromRequest(CreateAccountRequest request){
        Account account = new Account();
        account.setUsername(request.username());
        account.setMail(request.mail());
        account.setPassword(request.password());
        account.setMailStatus(MailStatus.CREATED);

        return account;
    }
}
