package com.lloyds.payments.ingestor.service;


import com.lloyds.payments.ingestor.dto.AccountRequest;

public interface AccountService {

    AccountRequest getAccount(String accountId);

}
