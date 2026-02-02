package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.commoncore.event.ProductCreatedEvent;
import com.codewithkz.inventoryservice.model.Inventory;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryCreateConsumer {
    private final InventoryServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topic.create-inventory-command}")
    public void handle(String event) throws Exception {
        try {
            log.info("Received create inventory event: {}", event);
            ProductCreatedEvent payload =
                    mapper.readValue(event, ProductCreatedEvent.class);
            Inventory inventory = new Inventory();
            inventory.setProductId(payload.getProductId());
            inventory.setQuantity(payload.getQuantity());
            service.create(inventory);
        } catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }
    }
}
