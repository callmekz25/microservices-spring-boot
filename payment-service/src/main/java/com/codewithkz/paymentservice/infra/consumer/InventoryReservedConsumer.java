package com.codewithkz.paymentservice.infra.consumer;

import com.codewithkz.paymentservice.infra.config.RabbitMQConfig;
import com.codewithkz.paymentservice.infra.event.InventoryReservedEvent;
import com.codewithkz.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReservedConsumer {
    private final PaymentService service;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RESERVED_QUEUE)
    public void handleInventoryReserved(InventoryReservedEvent event) {
        log.info("Received InventoryReserved event: {}", event.getOrderId());

        service.handleProcessPaymentEvent(event);
    }
}
