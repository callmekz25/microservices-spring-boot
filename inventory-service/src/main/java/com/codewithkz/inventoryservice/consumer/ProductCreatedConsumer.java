package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.inventoryservice.event.ProductCreatedEvent;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCreatedConsumer {
    private final InventoryServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topics.product-created}")
    public void handleProductCreated(String event) throws Exception {
        try {
            log.info("Received product created event: {}", event);
            ProductCreatedEvent payload =
                    mapper.readValue(event, ProductCreatedEvent.class);
            service.create(payload.getProductId(), payload.getQuantity());
        } catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }
    }
}
