package com.codewithkz.paymentservice.service.impl;

import com.codewithkz.commoncore.exception.BadRequestException;
import com.codewithkz.paymentservice.entity.OutboxEvent;
import com.codewithkz.paymentservice.entity.OutboxStatus;
import com.codewithkz.paymentservice.repository.OutboxRepository;
import com.codewithkz.paymentservice.service.OutboxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxServiceImpl implements OutboxService {
    private final OutboxRepository repo;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void save(String topic, Object payload) {
        try {
            repo.save(
                    OutboxEvent.builder()
                            .topic(topic)
                            .payload(objectMapper.writeValueAsString(payload))
                            .createdAt(Instant.now())
                            .status(OutboxStatus.PENDING)
                            .build()
            );
            log.info("Saved OutboxEvent: {}", payload);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

    }
}
