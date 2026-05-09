package com.lloyds.payments.ingestor.controller;


import com.lloyds.payments.ingestor.dto.AccountRequest;
import com.lloyds.payments.ingestor.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountRequest> getAccount(@PathVariable String accountId) {
        // Decode URL-encoded accountId (e.g., 20-15-88%2F43917265 -> 20-15-88/43917265)
        final String decodedAccountId = URLDecoder.decode(accountId, StandardCharsets.UTF_8);
        final AccountRequest account = accountService.getAccount(decodedAccountId);
        return ResponseEntity.ok(account);
    }

}
