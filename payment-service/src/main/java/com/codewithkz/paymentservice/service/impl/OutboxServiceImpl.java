package com.codewithkz.paymentservice.service.impl;

import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.paymentservice.model.OutboxEvent;
import com.codewithkz.paymentservice.repository.OutboxRepository;
import com.codewithkz.paymentservice.service.OutboxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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