package com.codewithkz.productservice.infra.rabbitmq.publisher;

import com.codewithkz.productservice.infra.rabbitmq.event.ProductCreatedEvent;
import com.codewithkz.productservice.infra.rabbitmq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ProductEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProductCreated(ProductCreatedEvent event) {
        log.info("Publishing product created event: {}", event.getProductId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PRODUCT_EXCHANGE,
                RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY,
                event
        );
    }
}