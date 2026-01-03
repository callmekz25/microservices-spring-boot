package com.codewithkz.orderservice.controller;

import com.codewithkz.orderservice.core.response.ApiResponse;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
