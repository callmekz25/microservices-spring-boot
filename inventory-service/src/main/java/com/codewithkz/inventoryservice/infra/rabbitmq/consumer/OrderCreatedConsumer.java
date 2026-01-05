package com.codewithkz.inventoryservice.infra.rabbitmq.consumer;

import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.infra.rabbitmq.event.InventoryRejectedEvent;
import com.codewithkz.inventoryservice.infra.rabbitmq.event.InventoryReservedEvent;
import com.codewithkz.inventoryservice.infra.rabbitmq.event.OrderCreatedEvent;
import com.codewithkz.inventoryservice.infra.rabbitmq.publisher.InventoryEventPublisher;
import com.codewithkz.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final InventoryService service;
    private final InventoryEventPublisher publisher;

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received order created event: {}", event.getOrderId());
        InventoryDto inventory = service.findByProductId(event.getProductId());

        boolean reserved = service.reserved(inventory.getProductId(), event.getQuantity());
        if(reserved) {
            InventoryReservedEvent eventReserved =  InventoryReservedEvent
                    .builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .amount(event.getTotal())
                    .build();

            publisher.publishInventoryReserved(eventReserved);
        } else {
            InventoryRejectedEvent eventRejected =  InventoryRejectedEvent
                    .builder().orderId(event.getOrderId()).build();

            publisher.publishInventoryRejected(eventRejected);
        }

    }
}
