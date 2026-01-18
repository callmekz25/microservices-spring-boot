package com.codewithkz.paymentservice.utils;

import com.codewithkz.paymentservice.config.RabbitMQConfig;
import com.codewithkz.paymentservice.event.InventoryReservedEvent;
import com.codewithkz.paymentservice.event.PaymentCompletedEvent;
import com.codewithkz.paymentservice.event.PaymentFailedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventRegistry {

    private final Map<String, Class<?>> map = Map.of(
            RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY, PaymentFailedEvent.class,
            RabbitMQConfig.PAYMENT_COMPLETED_ROUTING_KEY, PaymentCompletedEvent.class,
            RabbitMQConfig.INVENTORY_RESERVED_ROUTING_KEY, InventoryReservedEvent.class
    );

    public Class<?> get(String event) {
        return map.get(event);
    }
}