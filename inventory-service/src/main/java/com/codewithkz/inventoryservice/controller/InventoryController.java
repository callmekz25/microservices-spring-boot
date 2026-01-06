package com.codewithkz.inventoryservice.controller;


import com.codewithkz.inventoryservice.core.response.ApiResponse;
import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.service.InventoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryDto>>> getInventories(HttpServletRequest request) {
        log.info("User Id: {}", request.getHeader("X-User-Id"));
        log.info("Role: {}", request.getHeader("X-Role"));
        var result = service.findAll();

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ApiResponse<InventoryDto>> getInventoryByProductId(@PathVariable Long id){
        var result = service.findByProductId(id);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
