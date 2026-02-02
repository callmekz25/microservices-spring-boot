package com.codewithkz.inventoryservice.service.impl;


import com.codewithkz.commoncore.event.InventoryRejectedEvent;
import com.codewithkz.commoncore.event.InventoryReservedEvent;
import com.codewithkz.commoncore.exception.BadRequestException;
import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.commoncore.event.OrderCreatedEvent;
import com.codewithkz.commoncore.event.PaymentFailedEvent;
import com.codewithkz.commoncore.event.InventoryReleasedEvent;
import com.codewithkz.inventoryservice.dto.InventoryCreateUpdateRequestDTO;
import com.codewithkz.inventoryservice.model.Inventory;
import com.codewithkz.inventoryservice.repository.InventoryRepository;
import com.codewithkz.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class InventoryServiceImpl extends BaseServiceImpl<Inventory, String> implements InventoryService {

    private final InventoryRepository repo;
    private final OutboxServiceImpl outboxService;
    @Value("${app.kafka.topic.inventory-reserved}")
    private String inventoryReservedTopic;
    @Value("${app.kafka.topic.inventory-released}")
    private String inventoryReleasedTopic;
    @Value("${app.kafka.topic.inventory-rejected}")
    private String inventoryRejectTopic;

    public InventoryServiceImpl(BaseRepository<Inventory, String> repository, InventoryRepository repo,
                                OutboxServiceImpl outboxService) {
        super(repository);
        this.repo = repo;
        this.outboxService = outboxService;
    }


    @Override
    public Inventory getByProductId(String id) {
        return repo.getByProductId(id).orElseThrow(() -> new NotFoundException("Inventory not found for product id: " + id));
    }

    @Transactional
    @Override
    public Inventory validateStock(InventoryCreateUpdateRequestDTO dto) {
        Inventory inventory = this.getByProductId(dto.getProductId());
        boolean success = repo.decreaseQuantity(dto.getProductId(), dto.getQuantity()) == 1;
        if(!success) {
            throw new BadRequestException("Not enough stock for product id: " + dto.getProductId());
        }
        return inventory;
    }

    @Transactional
    @Override
    public void handleInventoryReserve(OrderCreatedEvent event) {

        boolean reserved = repo.decreaseQuantity(event.getProductId(), event.getQuantity()) == 1;
        if(reserved) {
            InventoryReservedEvent payload =  InventoryReservedEvent
                    .builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .build();


            outboxService.create(inventoryReservedTopic, payload);


        } else {
            InventoryRejectedEvent payload =  InventoryRejectedEvent
                    .builder().orderId(event.getOrderId()).build();

            outboxService.create(inventoryRejectTopic, payload);

        }
    }

    @Transactional
    @Override
    public void handlePaymentFailed(PaymentFailedEvent event) {

        boolean released = repo.increaseQuantity(event.getProductId(), event.getQuantity()) == 1;

        if(released) {
            InventoryReleasedEvent payload =
                    InventoryReleasedEvent
                            .builder()
                            .orderId(event.getOrderId()).build();

            outboxService.create(inventoryReleasedTopic, payload);
        } else {
            log.error("Failed to release inventory event: {}", event.getOrderId());
        }

    }


}
