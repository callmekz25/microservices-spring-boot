package com.codewithkz.inventoryservice.utils;


import com.codewithkz.inventoryservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.infra.rabbitmq.event.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventRegistry {

    private final Map<String, Class<?>> map = Map.of(
            RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY, ProductCreatedEvent.class,
            RabbitMQConfig.INVENTORY_RELEASED_ROUTING_KEY, InventoryReleasedEvent.class,
            RabbitMQConfig.INVENTORY_RESERVED_ROUTING_KEY, InventoryReservedEvent.class,
            RabbitMQConfig.INVENTORY_REJECTED_ROUTING_KEY, InventoryRejectedEvent.class,
            RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, OrderCreatedEvent.class,
            RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY, PaymentFailedEvent.class
    );

    public Class<?> get(String event) {
        return map.get(event);
    }
}
