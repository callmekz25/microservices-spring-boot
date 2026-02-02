package com.codewithkz.inventoryservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.inventoryservice.dto.InventoryCreateUpdateRequestDTO;
import com.codewithkz.inventoryservice.model.Inventory;
import com.codewithkz.commoncore.event.OrderCreatedEvent;
import com.codewithkz.commoncore.event.PaymentFailedEvent;

public interface InventoryService extends BaseService<Inventory, String> {
    Inventory getByProductId(String id);
    Inventory validateStock(InventoryCreateUpdateRequestDTO dto);
    void handleInventoryReserve(OrderCreatedEvent event);
    void handlePaymentFailed(PaymentFailedEvent event);
}
