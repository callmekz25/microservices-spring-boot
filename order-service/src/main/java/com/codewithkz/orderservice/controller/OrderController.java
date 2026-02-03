package com.codewithkz.orderservice.controller;

import com.codewithkz.commoncore.controller.BaseController;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.OrderCreateUpdateRequestDTO;
import com.codewithkz.orderservice.dto.OrderCreateUpdateResponseDTO;
import com.codewithkz.orderservice.model.Order;
import com.codewithkz.orderservice.mapper.OrderMapper;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController extends BaseController<Order, OrderCreateUpdateRequestDTO, OrderCreateUpdateResponseDTO, String> {

    private final OrderServiceImpl service;
    private final OrderMapper mapper;

    public OrderController(OrderServiceImpl service, OrderMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderCreateUpdateResponseDTO>>> getAll() {
        return super.getAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderCreateUpdateResponseDTO>> create(@RequestBody OrderCreateUpdateRequestDTO dto) {
        log.info("Product Id: {}", dto.getProductId());
        log.info("Quantity: {}", dto.getQuantity());
        Order source = mapper.toEntity(dto);
        Order created = service.create(source);
        OrderCreateUpdateResponseDTO responseDTO = mapper.toDTO(created);
        return ResponseEntity.ok(ApiResponse.success(responseDTO));
    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<OrderCreateUpdateResponseDTO>> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<OrderCreateUpdateResponseDTO>> update(@PathVariable String id, @RequestBody OrderCreateUpdateRequestDTO dto) {
        return super.update(id, dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        return super.delete(id);
    }
}
