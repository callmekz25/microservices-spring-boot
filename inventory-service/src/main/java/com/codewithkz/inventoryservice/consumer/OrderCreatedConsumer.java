package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.inventoryservice.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.event.OrderCreatedEvent;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final InventoryServiceImpl service;

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received order created event: {}", event.getOrderId());
        service.handleOrderCreated(event);

    }
}
