package com.codewithkz.inventoryservice.infra.rabbitmq.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderRabbitMQConfig {

    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
}
