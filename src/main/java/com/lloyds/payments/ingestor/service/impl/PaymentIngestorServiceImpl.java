package com.lloyds.payments.ingestor.service.impl;


import com.lloyds.payments.ingestor.dto.PaymentEvent;
import com.lloyds.payments.ingestor.dto.PaymentRequest;
import com.lloyds.payments.ingestor.entity.AccountEntity;
import com.lloyds.payments.ingestor.exception.PaymentIngestorException;
import com.lloyds.payments.ingestor.kafka.PaymentIngestorProducer;
import com.lloyds.payments.ingestor.repository.AccountRepository;
import com.lloyds.payments.ingestor.service.PaymentService;
import com.lloyds.payments.ingestor.util.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PaymentIngestorServiceImpl implements PaymentService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentIngestorProducer paymentIngestorProducer;


    @Override
    public String processPayment(final PaymentRequest paymentRequest) {
        if(paymentRequest.getDebitAccountId().equals(paymentRequest.getCreditAccountId())){
            throw new PaymentIngestorException("Debit and credit accounts cannot be the same", "ORD_400", 400);
        }
        AccountEntity debit = accountRepository.findById(paymentRequest.getDebitAccountId())
                .orElseThrow(() -> new PaymentIngestorException("Debit account not found", "ORD_404", 404));

        AccountEntity credit = accountRepository.findById(paymentRequest.getCreditAccountId())
                .orElseThrow(() -> new PaymentIngestorException("Credit account not found", "ORD_404", 404));

        if(!debit.getStatus().equals(AccountStatus.ACTIVE)
                || !credit.getStatus().equals(AccountStatus.ACTIVE)){
            throw new PaymentIngestorException("Account is suspended", "ORD_422", 422);
        }else{
            String paymentId = UUID.randomUUID().toString();
//            Map<String, Object> event = Map.of(
//                    "paymentId", paymentId,
//                    "debitAccountId", debit.getAccountId(),
//                    "creditAccountId", credit.getAccountId(),
//                    "amount", paymentRequest.getAmount(),
//                    "currency", paymentRequest.getCurrency(),
//                    "createdTime",LocalDateTime.now()
//            );

            PaymentEvent event = PaymentEvent.builder()
                    .paymentId(paymentId)
                    .debitAccountId(debit.getAccountId())
                    .creditAccountId(credit.getAccountId())
                    .amount(paymentRequest.getAmount())
                    .currency(paymentRequest.getCurrency())
                    .createdTime(LocalDateTime.now())
                    .build();
            paymentIngestorProducer.createPayment(event);
        }
        return paymentRequest.getPaymentId();
    }
}
