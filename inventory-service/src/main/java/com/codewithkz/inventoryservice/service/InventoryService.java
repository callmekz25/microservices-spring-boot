package com.codewithkz.inventoryservice.service;

import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.event.OrderCreatedEvent;
import com.codewithkz.inventoryservice.event.PaymentFailedEvent;

import java.util.List;

public interface InventoryService {
    List<InventoryDto> findAll();
    void create(Long productId, int quantity);
    InventoryDto findByProductId(Long id);
    void handleOrderCreated(OrderCreatedEvent event);
    void handlePaymentFailed(PaymentFailedEvent event);
}
