package com.codewithkz.orderservice.controller;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.CreateOrderDto;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> findAll() {
       var orders = service.findAll();

       return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> create(@RequestBody CreateOrderDto dto) {
        var order = service.create(dto);

        return ResponseEntity.ok(ApiResponse.success(order));
    }
}
