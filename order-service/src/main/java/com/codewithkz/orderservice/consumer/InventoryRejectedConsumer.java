package com.codewithkz.orderservice.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.config.RabbitMQConfig;
import com.codewithkz.orderservice.event.InventoryRejectedEvent;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryRejectedConsumer {

    private final OrderServiceImpl service;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_REJECTED_QUEUE)
    public void handleInventoryRejected(InventoryRejectedEvent event) {
        log.info("Received InventoryRejected event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.CANCELLED);

        log.info("Order cancelled");

    }
}
