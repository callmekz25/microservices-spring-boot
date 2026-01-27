package com.codewithkz.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${app.kafka.topic.payment-completed}")
    private String paymentCompletedTopicName;
    @Value("${app.kafka.topic.payment-failed}")
    private String paymentFailedTopicName;

    @Bean
    public NewTopic paymentCompletedTopic() {
        return TopicBuilder
                .name(paymentCompletedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder
                .name(paymentFailedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
