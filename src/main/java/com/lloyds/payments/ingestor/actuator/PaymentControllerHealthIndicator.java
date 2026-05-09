package com.lloyds.payments.ingestor.actuator;

import com.lloyds.payments.ingestor.kafka.PaymentIngestorProducer;
import com.lloyds.payments.ingestor.repository.AccountRepository;
import com.lloyds.payments.ingestor.service.PaymentService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PaymentControllerHealthIndicator implements HealthIndicator {

	private final PaymentService paymentService;
	private final PaymentIngestorProducer producer;
	private final AccountRepository accountRepository;

	public PaymentControllerHealthIndicator(PaymentService paymentService,
											PaymentIngestorProducer producer,
											AccountRepository accountRepository) {
		this.paymentService = paymentService;
		this.producer = producer;
		this.accountRepository = accountRepository;
	}

	@Override
	public Health health() {
		try {
			// Basic checks: PaymentService and producer beans available and DB reachable
			boolean servicePresent = this.paymentService != null;
			boolean producerPresent = this.producer != null;
			long accounts = this.accountRepository.count();
			if (servicePresent && producerPresent) {
				return Health.up()
						.withDetail("paymentService", "available")
						.withDetail("producer", "available")
						.withDetail("accounts.count", accounts)
						.build();
			}
			return Health.down().withDetail("reason", "missing beans").build();
		} catch (Exception ex) {
			return Health.down().withDetail("error", ex.getMessage()).build();
		}
	}
}


