package com.codewithkz.orderservice.infra.rabbitmq.publisher;


import com.codewithkz.orderservice.infra.rabbitmq.config.OrderRabbitMQConfig;
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

    public void publishOrderCreated(OrderCreatedEvent event) {
        log.info("Publishing order created event: {}", event.getOrderId());
        rabbitTemplate.convertAndSend(
                OrderRabbitMQConfig.ORDER_EXCHANGE,
                OrderRabbitMQConfig.ORDER_CREATED_ROUTING_KEY,
                event
        );
    }
}
