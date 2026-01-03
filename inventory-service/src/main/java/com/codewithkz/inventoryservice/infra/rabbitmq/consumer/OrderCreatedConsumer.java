package com.codewithkz.inventoryservice.infra.rabbitmq.consumer;

import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.infra.rabbitmq.config.OrderRabbitMQConfig;
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

    @RabbitListener(queues = OrderRabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {

        InventoryDto inventory = service.findByProductId(event.getProductId());

        if(inventory.getQuantity() >= event.getQuantity()) {
            service.decrease(inventory.getProductId(), event.getQuantity());

            InventoryReservedEvent eventReserved =  InventoryReservedEvent
                    .builder().orderId(event.getOrderId()).build();

            publisher.publishInventoryReserved(eventReserved);
        } else {
            InventoryRejectedEvent eventRejected =  InventoryRejectedEvent
                    .builder().orderId(event.getOrderId()).build();

            publisher.publishInventoryRejected(eventRejected);
        }

    }
}
