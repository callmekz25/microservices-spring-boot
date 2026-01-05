package com.codewithkz.paymentservice.infra.publisher;

import com.codewithkz.paymentservice.infra.config.RabbitMQConfig;
import com.codewithkz.paymentservice.infra.event.PaymentCompletedEvent;
import com.codewithkz.paymentservice.infra.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAYMENT_EXCHANGE,
                RabbitMQConfig.PAYMENT_COMPLETED_ROUTING_KEY,
                event);

        log.info("Publishing Payment Completed Event: {}", event.getOrderId());

    }

    public void publishPaymentFailed(PaymentFailedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAYMENT_EXCHANGE,
                RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY,
                event);

        log.info("Publishing Payment Failed Event: {}", event.getOrderId());
    }
}
