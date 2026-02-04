package com.codewithkz.paymentservice.consumer;

import com.codewithkz.commoncore.event.CreatePaymentEvent;
import com.codewithkz.paymentservice.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentCreateConsumer {
    private final PaymentServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topic.create-payment-command}")
    public void handle(String event) throws Exception {
        try {
            log.info("Received CreatePaymentEvent event: {}", event);
            CreatePaymentEvent payload =
                    mapper.readValue(event, CreatePaymentEvent.class);

            service.handleProcessPaymentEvent(payload);
        } catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }
    }
}
