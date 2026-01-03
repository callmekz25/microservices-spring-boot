package com.codewithkz.inventoryservice.infra.rabbitmq.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductRabbitMQConfig {

    public static final String PRODUCT_CREATED_QUEUE = "product.created.queue";
}
