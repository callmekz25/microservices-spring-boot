package com.codewithkz.orderservice.service.impl;

import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.orderservice.model.OutboxEvent;
import com.codewithkz.orderservice.repository.OutboxRepository;
import com.codewithkz.orderservice.service.OutboxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class OutboxServiceImpl extends BaseServiceImpl<OutboxEvent, String> implements OutboxService {
    private final ObjectMapper objectMapper;
    private final OutboxRepository repo;

    public OutboxServiceImpl(OutboxRepository repository, ObjectMapper objectMapper) {
        super(repository);
        this.objectMapper = objectMapper;
        this.repo = repository;
    }

    @Override
    public void create(String topic, Object payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);

            OutboxEvent event = new OutboxEvent();
            event.setTopic(topic);
            event.setPayload(json);

            repo.save(event);
        } catch (Exception e) {
            throw new RuntimeException("Cannot serialize outbox payload", e);
        }
    }
}
