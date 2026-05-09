package com.lloyds.payments.ingestor.controller;


import com.lloyds.payments.ingestor.dto.PaymentRequest;
import com.lloyds.payments.ingestor.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<Map<String, String>> createPayment(
             @RequestBody PaymentRequest request) {

        final String paymentId = paymentService.processPayment(request);
        return ResponseEntity
                .accepted()
                .body(Map.of("paymentId", paymentId));
    }
}
