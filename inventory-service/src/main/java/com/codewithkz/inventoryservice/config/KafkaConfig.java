package com.codewithkz.inventoryservice.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    @Value("${app.kafka.topics.inventory-released}")
    private String inventoryReleasedTopic;
    @Value("${app.kafka.topics.inventory-reserved}")
    private String inventoryReservedTopic;
    @Value("${app.kafka.topics.inventory-rejected}")
    private String inventoryRejectedTopic;

    @Bean
    public NewTopic inventoryReserved() {
        return TopicBuilder.name(inventoryReservedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inventoryRejected() {
        return TopicBuilder.name(inventoryRejectedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inventoryReleased() {
        return TopicBuilder.name(inventoryReleasedTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
