package com.codewithkz.orderservice.infra.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    public static final String INVENTORY_EXCHANGE = "inventory.exchange";
    public static final String INVENTORY_REJECTED_QUEUE = "order.inventory.rejected";
    public static final String INVENTORY_REJECTED_ROUTING_KEY = "inventory.rejected";
    public static final String INVENTORY_RELEASED_QUEUE = "order.inventory.released";
    public static final String INVENTORY_RELEASED_ROUTING_KEY = "inventory.released";

    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_COMPLETED_ROUTING_KEY = "payment.completed";
    public static final String PAYMENT_COMPLETED_QUEUE = "order.payment.completed";



    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public DirectExchange inventoryExchange() {
        return new DirectExchange(INVENTORY_EXCHANGE);
    }


    @Bean
    public Queue inventoryRejectedQueue() {
        return new Queue(INVENTORY_REJECTED_QUEUE);
    }

    @Bean
    public Binding inventoryRejectedBinding() {
        return BindingBuilder
                .bind(inventoryRejectedQueue())
                .to(inventoryExchange())
                .with(INVENTORY_REJECTED_ROUTING_KEY);
    }

    @Bean
    public Queue inventoryReleasedQueue() {
        return new Queue(INVENTORY_RELEASED_QUEUE);
    }

    @Bean
    public Binding inventoryReleasedBinding() {
        return BindingBuilder
                .bind(inventoryReleasedQueue())
                .to(inventoryExchange())
                .with(INVENTORY_RELEASED_ROUTING_KEY);
    }


    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(PAYMENT_EXCHANGE);
    }

    @Bean
    public Queue paymentCompletedQueue() {
        return new Queue(PAYMENT_COMPLETED_QUEUE);
    }

    @Bean
    public Binding paymentCompletedBinding() {
        return BindingBuilder
                .bind(paymentCompletedQueue())
                .to(paymentExchange())
                .with(PAYMENT_COMPLETED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}