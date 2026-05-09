package com.lloyds.payments.ingestor.dto;

import com.lloyds.payments.ingestor.util.Currency;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest {

//    @NotNull(message = "Payment ID must not be blank")
    private String paymentId;

    @NotBlank(message = "Debit account ID must not be blank")
    private String debitAccountId;

    @NotBlank(message = "Credit account ID must not be blank")
    private String creditAccountId;

    @NotNull(message = "Amount must be greater than 0")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Currency must be a 3-character ISO code")
    @Size(min = 3, max = 3, message = "Currency must be a 3-character ISO code")
    private Currency currency;

    @Size(max = 35, message = "Reference must not exceed 35 characters")
    private String reference;

    @NotNull(message = "Timestamp must not be in the future")
    @PastOrPresent(message = "Timestamp must not be in the future")
    private Instant timestamp;
}
