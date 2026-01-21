package com.codewithkz.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    public final static String ORDER_TOPIC = "order-topic";

    @Bean
    public NewTopic orderEventsTopic() {
        return TopicBuilder
                .name(ORDER_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
