package com.lloyds.payments.ingestor.service.impl;


import com.lloyds.payments.ingestor.dto.AccountRequest;
import com.lloyds.payments.ingestor.entity.AccountEntity;
import com.lloyds.payments.ingestor.exception.PaymentIngestorException;
import com.lloyds.payments.ingestor.repository.AccountRepository;
import com.lloyds.payments.ingestor.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public AccountRequest getAccount(String accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId)
                .orElseThrow(() -> new PaymentIngestorException("Account not found", "ORD_404", 404));
        return modelMapper.map(accountEntity, AccountRequest.class);
    }

}
