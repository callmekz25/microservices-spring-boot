package com.codewithkz.inventoryservice.publisher;

import com.codewithkz.inventoryservice.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.event.InventoryRejectedEvent;
import com.codewithkz.inventoryservice.event.InventoryReleasedEvent;
import com.codewithkz.inventoryservice.event.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishInventoryReserved(InventoryReservedEvent event) {
        log.info("Publishing inventory reserved event: {}", event.getOrderId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.INVENTORY_RESERVED_ROUTING_KEY,
                event);
    }

    public void publishInventoryRejected(InventoryRejectedEvent event) {
        log.info("Publishing inventory rejected event: {}", event.getOrderId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.INVENTORY_REJECTED_ROUTING_KEY,
                event);
    }

    public void publishInventoryReleased(InventoryReleasedEvent event) {
        log.info("Publishing inventory released event: {}", event.getOrderId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.INVENTORY_RELEASED_ROUTING_KEY,
                event
        );

    }


}
