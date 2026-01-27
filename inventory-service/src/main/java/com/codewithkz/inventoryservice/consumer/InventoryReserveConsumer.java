package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.inventoryservice.event.OrderCreatedEvent;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReserveConsumer {
    private final InventoryServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topic.reserve-inventory-command}")
    public void handle(String event) throws Exception {
        try {
            log.info("Received order created event: {}", event);
            OrderCreatedEvent payload =
                    mapper.readValue(event, OrderCreatedEvent.class);
            service.handleInventoryReserve(payload);

        }catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }

    }
}
