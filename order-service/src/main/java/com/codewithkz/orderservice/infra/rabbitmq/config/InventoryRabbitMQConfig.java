package com.codewithkz.orderservice.infra.rabbitmq.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryRabbitMQConfig {

    public static final String INVENTORY_RESERVED_QUEUE = "inventory.reserved.queue";
    public static final String INVENTORY_REJECTED_QUEUE = "inventory.rejected.queue";

}
