package com.codewithkz.inventoryservice.controller;


import com.codewithkz.inventoryservice.core.response.ApiResponse;
import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryDto>>> getInventories(){
        var result = service.findAll();

        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
