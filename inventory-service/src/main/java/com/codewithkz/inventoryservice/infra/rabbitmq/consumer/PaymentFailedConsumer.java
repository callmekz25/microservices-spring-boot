package com.codewithkz.inventoryservice.infra.rabbitmq.consumer;

import com.codewithkz.inventoryservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.infra.rabbitmq.event.PaymentFailedEvent;
import com.codewithkz.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedConsumer {
    private final InventoryService service;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_FAILED_QUEUE)
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.info("Received payment failed event: {}", event.getOrderId());

        service.handlePaymentFailed(event);
    }
}
