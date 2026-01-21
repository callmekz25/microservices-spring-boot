package com.codewithkz.inventoryservice.publisher;

import com.codewithkz.inventoryservice.entity.OutboxEvent;
import com.codewithkz.inventoryservice.entity.OutboxStatus;
import com.codewithkz.inventoryservice.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.repository.OutboxRepository;
import com.codewithkz.inventoryservice.utils.EventRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxSchedule {

    private final RabbitTemplate rabbitTemplate;
    private final OutboxRepository repo;
    private final ObjectMapper objectMapper;
    private final EventRegistry eventRegistry;

//    @Scheduled(fixedDelay = 1000)
//    @Transactional
//    public void publish() {
//
//        List<OutboxEvent> events =
//                repo.findReadyToPublish(Instant.now());
//
//        for (OutboxEvent e : events) {
//            try {
//
//                var eventClass = eventRegistry.get(e.getEvent());
//
//                var payload = objectMapper.readValue(e.getPayload(), eventClass);
//
//                rabbitTemplate.convertAndSend(
//                        RabbitMQConfig.INVENTORY_EXCHANGE,
//                        e.getDestination(),
//                        payload
//                );
//
//                e.setStatus(OutboxStatus.COMPLETED);
//                log.info("Published event: " + e.getEvent());
//            } catch (Exception ex) {
//                e.setRetryCount(e.getRetryCount() + 1);
//
//                if(e.getRetryCount() >= 3) {
//                    e.setStatus(OutboxStatus.DEAD);
//                } else {
//                    e.setStatus(OutboxStatus.FAILED);
//                    e.setTimeRetry(Instant.now().plusSeconds(5 * e.getRetryCount()));
//                }
//
//                log.error("Published event failed: " + e.getEvent());
//            }
//        }
//    }

}
