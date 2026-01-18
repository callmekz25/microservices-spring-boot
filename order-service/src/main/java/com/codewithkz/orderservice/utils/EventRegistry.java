package com.codewithkz.orderservice.utils;

import com.codewithkz.orderservice.config.RabbitMQConfig;
import com.codewithkz.orderservice.event.InventoryRejectedEvent;
import com.codewithkz.orderservice.event.InventoryReleasedEvent;
import com.codewithkz.orderservice.event.OrderCreatedEvent;
import com.codewithkz.orderservice.event.PaymentCompletedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventRegistry {

    private final Map<String, Class<?>> map = Map.of(
            RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, OrderCreatedEvent.class,
            RabbitMQConfig.PAYMENT_COMPLETED_ROUTING_KEY, PaymentCompletedEvent.class,
            RabbitMQConfig.INVENTORY_REJECTED_ROUTING_KEY, InventoryRejectedEvent.class,
            RabbitMQConfig.INVENTORY_RELEASED_ROUTING_KEY, InventoryReleasedEvent.class
    );

    public Class<?> get(String event) {
        return map.get(event);
    }
}