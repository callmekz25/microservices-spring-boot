package com.codewithkz.orderservice.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.event.InventoryRejectedEvent;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryRejectedConsumer {

    private final OrderServiceImpl service;

   @KafkaListener(topics = "${app.kafka.topic.inventory-rejected}")
    public void handle(InventoryRejectedEvent event) {
        log.info("Received InventoryRejected event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.CANCELLED);

        log.info("Order cancelled");

    }
}
