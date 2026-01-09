package com.codewithkz.paymentservice.infra.outbox;

import com.codewithkz.paymentservice.core.exception.BadRequestException;
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
public class OutboxService {
    private final OutboxRepository repo;
    private final ObjectMapper objectMapper;

    @Transactional
    public void save(String event, String destination, Object payload) {
        try {
            repo.save(
                    OutboxEvent.builder()
                            .event(event)
                            .destination(destination)
                            .payload(objectMapper.writeValueAsString(payload))
                            .createdAt(Instant.now())
                            .status(OutboxStatus.PENDING)
                            .build()
            );
            log.info("Saved OutboxEvent: {}", event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

    }
}
