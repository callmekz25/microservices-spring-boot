package com.codewithkz.productservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    @Value("${app.kafka.topics.product-created}")
    private String productCreatedTopic;


    @Bean
    public NewTopic productCreatedTopic() {
        return TopicBuilder.name(productCreatedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
