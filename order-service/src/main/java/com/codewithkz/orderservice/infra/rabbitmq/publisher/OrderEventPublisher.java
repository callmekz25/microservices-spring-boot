package com.codewithkz.orderservice.infra.rabbitmq.publisher;


import com.codewithkz.orderservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.orderservice.infra.rabbitmq.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProductCreated(OrderCreatedEvent event) {
        log.info("Publishing order created event: {}", event.getOrderId());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_CREATED_ROUTING_KEY,
                event
        );
    }
}
