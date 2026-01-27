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
    @Value("${app.kafka.topic.create-inventory-command}")
    private String createInventoryCommandTopic;


    @Bean
    public NewTopic createInventoryTopic() {
        return TopicBuilder.name(createInventoryCommandTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
