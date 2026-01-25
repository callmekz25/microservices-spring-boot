package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.inventoryservice.event.PaymentFailedEvent;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedConsumer {
    private final InventoryServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topics.payment-failed}")
    public void handlePaymentFailed(String event) throws Exception
    {
        try {
            log.info("Received payment failed event: {}", event);
            PaymentFailedEvent payload = mapper.readValue(event, PaymentFailedEvent.class);
            service.handlePaymentFailed(payload);

        } catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }
    }
}
