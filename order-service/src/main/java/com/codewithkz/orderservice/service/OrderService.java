package com.codewithkz.orderservice.service;

import com.codewithkz.orderservice.dto.CreateOrderDto;
import com.codewithkz.orderservice.dto.InventoryDto;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.entity.Order;
import com.codewithkz.orderservice.infra.client.InventoryClient;
import com.codewithkz.orderservice.mapper.OrderMapper;
import com.codewithkz.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository repo, OrderMapper mapper, InventoryClient inventoryClient) {
        this.repo = repo;
        this.mapper = mapper;
        this.inventoryClient = inventoryClient;
    }


    public List<OrderDto> findAll() {
        List<Order> orders = repo.findAll();

        return mapper.toDtoList(orders);
    }

//    public OrderDto create(CreateOrderDto dto) {
//
//        var order = new Order();
//
//        InventoryDto inventory = inventoryClient.getInventoryByProductId(dto.getProductId()).getData();
//
//        order.setProductId(dto.getProductId());
//        order.setQuantity(dto.getQuantity());
//        order.setUserId(dto.getUserId());
//
//    }




}
