package com.codewithkz.orderservice.infra.outbox;

import com.codewithkz.orderservice.infra.rabbitmq.config.RabbitMQConfig;
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

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publish() {

        List<OutboxEvent> events =
                repo.findReadyToPublish(Instant.now());

        for (OutboxEvent e : events) {
            try {
                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.ORDER_EXCHANGE,
                        e.getDestination(),
                        e.getPayload()
                );

                e.setStatus(OutboxStatus.COMPLETED);
                log.info("Published event: " + e.getDestination());
            } catch (Exception ex) {
                e.setRetryCount(e.getRetryCount() + 1);

                if(e.getRetryCount() >= 3) {
                    e.setStatus(OutboxStatus.DEAD);
                } else {
                    e.setStatus(OutboxStatus.FAILED);
                    e.setTimeRetry(Instant.now().plusSeconds(5 * e.getRetryCount()));
                }

                log.error("Published event failed: " + e.getDestination());
            }
        }
    }

}
