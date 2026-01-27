package com.codewithkz.orderservice.service;

import com.codewithkz.orderservice.dto.CreateOrderDto;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.event.InventoryReservedEvent;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    OrderDto create(CreateOrderDto dto);
    void updateStatusOrder(Long orderId, OrderStatus status);
    void handleCreatePaymentCommand(InventoryReservedEvent event);
}
