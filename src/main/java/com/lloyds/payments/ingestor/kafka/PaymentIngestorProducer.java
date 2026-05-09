package com.lloyds.payments.ingestor.kafka;


import com.lloyds.payments.ingestor.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentIngestorProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    @Value("${payment-ingestor.kafka.producer.topics.payment-submitted}")
//    private String PAYMENT_SUBMITTED_TOPIC;


    public void createPayment(PaymentEvent paymentEvent){
        log.info(
                "Publishing payment event paymentId={}, debitAccountId={}",
                paymentEvent.getPaymentId(),
                paymentEvent.getDebitAccountId()
        );

//        Message<PaymentEvent> message = MessageBuilder
//                .withPayload(paymentEvent)
//                .setHeader(KafkaHeaders.TOPIC, paymentTopic)
//                .setHeader(KafkaHeaders.MESSAGE_KEY,
//                        paymentEvent.getDebitAccountId())
//                .setHeader("correlationId",
//                        UUID.randomUUID().toString())
//                .build();
//        kafkaTemplate.send(message);

        kafkaTemplate.send("payments.submitted", paymentEvent.getDebitAccountId(), paymentEvent)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info(
                                "Message published successfully topic={}, partition={}, offset={}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    } else {
                        log.error(
                                "Kafka publish failed paymentId={}",
                                paymentEvent.getPaymentId(),
                                ex
                        );
                    }
                });
    }
}
