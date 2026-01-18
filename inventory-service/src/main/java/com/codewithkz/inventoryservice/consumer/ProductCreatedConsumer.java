package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.inventoryservice.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.event.ProductCreatedEvent;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductCreatedConsumer {

    private final InventoryServiceImpl service;
    public ProductCreatedConsumer(InventoryServiceImpl service) {
        this.service = service;
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATED_QUEUE)
    public void onProductCreated(ProductCreatedEvent event) {
        log.info("Received product created event: {}", event.getProductId());
        service.create(event.getProductId(), event.getQuantity());
    }
}
