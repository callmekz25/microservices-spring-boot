package com.codewithkz.orderservice.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.event.InventoryReleasedEvent;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReleasedConsumer {
    private final OrderServiceImpl service;

    @KafkaListener(topics = "${app.kafka.topic.inventory-released}")
    public void handleInventoryReleased(InventoryReleasedEvent event) {
        log.info("Received InventoryReleased event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.CANCELLED);



    }
}
