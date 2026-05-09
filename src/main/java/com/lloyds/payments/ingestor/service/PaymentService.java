package com.lloyds.payments.ingestor.service;


import com.lloyds.payments.ingestor.dto.PaymentRequest;

public interface PaymentService {

    String processPayment( PaymentRequest request);
}
