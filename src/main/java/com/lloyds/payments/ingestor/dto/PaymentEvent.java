package com.lloyds.payments.ingestor.dto;

import com.lloyds.payments.ingestor.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentEvent {

    private String paymentId;

    private String debitAccountId;

    private String creditAccountId;

    private BigDecimal amount;

    private Currency currency;

    private String reference;

    private LocalDateTime createdTime;
}
