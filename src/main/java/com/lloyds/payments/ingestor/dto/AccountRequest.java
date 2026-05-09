package com.lloyds.payments.ingestor.dto;

import com.lloyds.payments.ingestor.util.AccountStatus;
import com.lloyds.payments.ingestor.util.AccountType;
import com.lloyds.payments.ingestor.util.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequest {

    private String accountId;

    private String accountName;

    private AccountType accountType;

    private AccountStatus status;

    private Currency currency;

    private LocalDate openedDate;

}
