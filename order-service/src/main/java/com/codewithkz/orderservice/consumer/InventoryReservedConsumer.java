package com.codewithkz.orderservice.consumer;


import com.codewithkz.commoncore.event.InventoryReservedEvent;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReservedConsumer {
    private final OrderServiceImpl service;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "${app.kafka.topic.inventory-reserved}")
    public void handle(String event) throws Exception {
        try {
            log.info("Received inventory reserved event: {}", event);
            InventoryReservedEvent payload =
                    mapper.readValue(event, InventoryReservedEvent.class);

            service.handleCreatePaymentCommand(payload);
        } catch (Exception e) {
            log.error("Failed to process message: {}", event, e);
            throw e;
        }
    }
}
