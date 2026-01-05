package com.codewithkz.orderservice.infra.rabbitmq.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.orderservice.infra.rabbitmq.event.InventoryReleasedEvent;
import com.codewithkz.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReleasedConsumer {
    private final OrderService service;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RELEASED_QUEUE)
    public void handleInventoryReleased(InventoryReleasedEvent event) {
        log.info("Received InventoryReleased event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.CANCELLED);



    }
}
