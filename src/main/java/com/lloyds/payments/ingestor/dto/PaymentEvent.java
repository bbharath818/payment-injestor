package com.lloyds.payments.ingestor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentEvent {

    private String paymentId;

    private String debitAccountId;

    private String creditAccountId;

    private BigDecimal amount;

    private String currency;

    private String reference;

    private Instant timestamp;
}
