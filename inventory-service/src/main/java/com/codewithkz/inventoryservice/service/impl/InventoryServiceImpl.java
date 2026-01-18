package com.codewithkz.inventoryservice.service.impl;


import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.entity.Inventory;
import com.codewithkz.inventoryservice.config.RabbitMQConfig;
import com.codewithkz.inventoryservice.event.*;
import com.codewithkz.inventoryservice.mapper.InventoryMapper;
import com.codewithkz.inventoryservice.repository.InventoryRepository;
import com.codewithkz.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repo;
    private final InventoryMapper mapper;
    private final OutboxServiceImpl outboxService;


    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<InventoryDto> findAll() {
        List<Inventory> inventories = repo.findAll();

        return mapper.toDtoList(inventories);
    }

    @Override
    public void create(Long productId, int quantity) {
        Inventory inventory = Inventory.builder().productId(productId).quantity(quantity).build();

        repo.save(inventory);
    }

    @Override
    public InventoryDto findByProductId(Long id) {
        Inventory inventory = repo.findByProductId(id).orElseThrow(() -> new NotFoundException("Inventory not found"));

        return mapper.toDto(inventory);
    }




    @Override
    @Transactional
    public void handleOrderCreated(OrderCreatedEvent event) {

        boolean reserved = repo.decreaseQuantity(event.getProductId(), event.getQuantity()) == 1;
        if(reserved) {
            InventoryReservedEvent eventReserved =  InventoryReservedEvent
                    .builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .amount(event.getTotal())
                    .build();

            outboxService.save(RabbitMQConfig.INVENTORY_RESERVED_ROUTING_KEY, RabbitMQConfig.INVENTORY_RESERVED_ROUTING_KEY, eventReserved);


        } else {
            InventoryRejectedEvent eventRejected =  InventoryRejectedEvent
                    .builder().orderId(event.getOrderId()).build();

            outboxService.save(RabbitMQConfig.INVENTORY_REJECTED_ROUTING_KEY, RabbitMQConfig.INVENTORY_REJECTED_ROUTING_KEY, eventRejected);

        }
    }

    @Override
    @Transactional
    public void handlePaymentFailed(PaymentFailedEvent event) {

        boolean released = repo.increaseQuantity(event.getProductId(), event.getQuantity()) == 1;

        if(released) {
            InventoryReleasedEvent eventReleased =
                    InventoryReleasedEvent
                            .builder()
                            .orderId(event.getOrderId()).build();

            outboxService.save(RabbitMQConfig.INVENTORY_RELEASED_ROUTING_KEY, RabbitMQConfig.INVENTORY_RELEASED_ROUTING_KEY, eventReleased);


        } else {
            log.error("Failed to release inventory event: {}", event.getOrderId());
        }

    }


}
