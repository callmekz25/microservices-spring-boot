package com.codewithkz.inventoryservice.consumer;

import com.codewithkz.inventoryservice.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.event.PaymentFailedEvent;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedConsumer {
    private final InventoryServiceImpl service;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_FAILED_QUEUE)
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.info("Received payment failed event: {}", event.getOrderId());

        service.handlePaymentFailed(event);
    }
}
