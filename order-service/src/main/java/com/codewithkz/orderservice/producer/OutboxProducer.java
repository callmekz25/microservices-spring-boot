package com.codewithkz.orderservice.producer;

import com.codewithkz.orderservice.entity.OutboxEvent;
import com.codewithkz.orderservice.entity.OutboxStatus;
import com.codewithkz.orderservice.config.RabbitMQConfig;
import com.codewithkz.orderservice.repository.OutboxRepository;
import com.codewithkz.orderservice.utils.EventRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
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
////                var eventClass = eventRegistry.get(e.getEvent());
////
////                var payload = objectMapper.readValue(e.getPayload(), eventClass);
//
//                kafkaTemplate.send(
//                        e.getTopic(),
//                        e.getEventId(),
//                        e.getPayload()
//                );
//
//                e.setStatus(OutboxStatus.COMPLETED);
//                log.info("Published event: " + e.getTopic());
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
//                log.error("Published event failed: " + e.getTopic());
//            }
//        }
//    }

}
