package com.codewithkz.inventoryservice.infra.rabbitmq.consumer;

import com.codewithkz.inventoryservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.infra.rabbitmq.event.ProductCreatedEvent;
import com.codewithkz.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductCreatedConsumer {

    private final InventoryService service;
    public ProductCreatedConsumer(InventoryService service) {
        this.service = service;
    }

    @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATED_QUEUE)
    public void onProductCreated(ProductCreatedEvent event) {
        log.info("Received product created event: {}", event.getProductId());
        service.create(event.getProductId(), event.getQuantity());
    }
}
