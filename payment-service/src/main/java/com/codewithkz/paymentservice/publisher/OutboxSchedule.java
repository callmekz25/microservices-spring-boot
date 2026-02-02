package com.codewithkz.paymentservice.publisher;


import com.codewithkz.paymentservice.model.OutboxEvent;
import com.codewithkz.commoncore.enums.OutboxStatus;
import com.codewithkz.paymentservice.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxSchedule {

    private final OutboxRepository repo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void publish() {

        List<OutboxEvent> events =
                repo.findReadyToPublish(Instant.now());

        for (OutboxEvent e : events) {
            try {

                kafkaTemplate.send(e.getTopic(), e.getPayload());


                e.setStatus(OutboxStatus.COMPLETED);
                log.info("Published topic: " + e.getTopic());
            } catch (Exception ex) {
                e.setRetryCount(e.getRetryCount() + 1);

                if(e.getRetryCount() >= 3) {
                    e.setStatus(OutboxStatus.DEAD);
                } else {
                    e.setStatus(OutboxStatus.FAILED);
                    e.setTimeRetry(Instant.now().plusSeconds(5 * e.getRetryCount()));
                }

                log.error("Published topic failed: " + e.getTopic());
            }
        }
    }

}
