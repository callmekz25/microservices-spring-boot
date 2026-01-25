package com.codewithkz.inventoryservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.kafka.topic")
public class KafkaTopicProperties {
    private String inventoryReserved;
    private String inventoryRejected;
    private String inventoryReleased;
}
