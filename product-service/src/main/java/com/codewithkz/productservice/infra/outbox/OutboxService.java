package com.codewithkz.orderservice.infra.outbox;

import com.codewithkz.orderservice.core.exception.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                            .build()
            );
            log.info("Saved OutboxEvent: {}", event);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

    }
}
