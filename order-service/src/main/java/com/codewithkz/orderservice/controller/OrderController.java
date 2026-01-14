package com.codewithkz.orderservice.controller;

import com.codewithkz.commoncore.constant.GatewayHeaders;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.CreateOrderDto;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
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
    public ResponseEntity<ApiResponse<OrderDto>> create(HttpServletRequest request, @RequestBody CreateOrderDto dto) {
        var userId = Long.parseLong((String) request.getAttribute("userId"));
        log.info("userId = " + userId);
        dto.setUserId(userId);
        var order = service.create(dto);

        return ResponseEntity.ok(ApiResponse.success(order));
    }
}
