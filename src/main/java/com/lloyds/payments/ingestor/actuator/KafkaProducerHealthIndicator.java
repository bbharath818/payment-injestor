package com.lloyds.payments.ingestor.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerHealthIndicator implements HealthIndicator {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerHealthIndicator(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Health health() {
        try {
            // Avoid producing a message for health checks. Requesting partition info validates producer/broker connectivity.
            kafkaTemplate.execute(producer -> {
                producer.partitionsFor("payments.submitted");
                return Boolean.TRUE;
            });
            return Health.up().withDetail("KafkaProducer", "Available").build();
        } catch (Exception e) {
            return Health.down().withDetail("KafkaProducer", e.getMessage()).build();
        }
    }
}
