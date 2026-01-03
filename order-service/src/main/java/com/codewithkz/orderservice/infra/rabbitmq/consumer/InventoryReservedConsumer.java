package com.codewithkz.orderservice.infra.rabbitmq.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.infra.rabbitmq.config.InventoryRabbitMQConfig;
import com.codewithkz.orderservice.infra.rabbitmq.event.InventoryReservedEvent;
import com.codewithkz.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReservedConsumer {

    private final OrderService service;

    @RabbitListener(queues = InventoryRabbitMQConfig.INVENTORY_RESERVED_QUEUE)
    public void handleInventoryReserved(InventoryReservedEvent event) {
        log.info("Received InventoryReserved event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.COMPLETED);

        log.info("Order completed");

    }
}
