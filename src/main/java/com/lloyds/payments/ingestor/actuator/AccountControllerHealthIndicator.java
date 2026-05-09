package com.lloyds.payments.ingestor.actuator;

import com.lloyds.payments.ingestor.repository.AccountRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class AccountControllerHealthIndicator implements HealthIndicator {

    private final AccountRepository accountRepository;

    public AccountControllerHealthIndicator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Health health() {
        try {
            long count = accountRepository.count();
            return Health.up().withDetail("accounts.count", count).build();
        } catch (Exception ex) {
            return Health.down().withDetail("accounts.error", ex.getMessage()).build();
        }
    }
}

