package com.codewithkz.productservice.utils;

import com.codewithkz.productservice.config.RabbitMQConfig;
import com.codewithkz.productservice.event.ProductCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventRegistry {

    private final Map<String, Class<?>> map = Map.of(
            RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY, ProductCreatedEvent.class
    );

    public Class<?> get(String event) {
        return map.get(event);
    }
}
