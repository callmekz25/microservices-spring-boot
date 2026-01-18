package com.codewithkz.paymentservice.consumer;

import com.codewithkz.paymentservice.config.RabbitMQConfig;
import com.codewithkz.paymentservice.event.InventoryReservedEvent;
import com.codewithkz.paymentservice.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReservedConsumer {
    private final PaymentServiceImpl service;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RESERVED_QUEUE)
    public void handleInventoryReserved(InventoryReservedEvent event) {
        log.info("Received InventoryReserved event: {}", event.getOrderId());

        service.handleProcessPaymentEvent(event);
    }
}
