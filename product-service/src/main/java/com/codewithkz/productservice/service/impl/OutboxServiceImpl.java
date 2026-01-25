package com.codewithkz.productservice.service.impl;


import com.codewithkz.productservice.entity.OutboxEvent;
import com.codewithkz.productservice.repository.OutboxRepository;
import com.codewithkz.productservice.entity.OutboxStatus;
import com.codewithkz.productservice.service.OutboxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxServiceImpl implements OutboxService {
    private final ObjectMapper objectMapper;
    private final OutboxRepository repo;

    public void save(String topic, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);

            OutboxEvent event = OutboxEvent.builder()
                    .topic(topic)
                    .payload(json)
                    .status(OutboxStatus.PENDING)
                    .createdAt(Instant.now())
                    .build();

            repo.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Cannot serialize outbox payload", e);
        }
    }
}
