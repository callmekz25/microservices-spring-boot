package com.codewithkz.orderservice.consumer;

import com.codewithkz.orderservice.model.OrderStatus;
import com.codewithkz.commoncore.event.PaymentCompletedEvent;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCompletedConsumer {
    private final OrderServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topic.payment-completed}")
    public void handle(String event) throws Exception {
        try {
            log.info("Received payment completed event: {}", event);
            PaymentCompletedEvent payload =
                    mapper.readValue(event, PaymentCompletedEvent.class);

            service.updateStatusOrder(payload.getOrderId(), OrderStatus.COMPLETED);
        } catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }
    }
}
