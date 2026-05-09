package com.lloyds.payments.ingestor.entity;

import com.lloyds.payments.ingestor.util.AccountStatus;
import com.lloyds.payments.ingestor.util.AccountType;
import com.lloyds.payments.ingestor.util.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "opened_date")
    private LocalDate openedDate;

}
