package com.codewithkz.inventoryservice.controller;


import com.codewithkz.commoncore.controller.BaseController;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.inventoryservice.dto.InventoryCreateUpdateRequestDTO;
import com.codewithkz.inventoryservice.dto.InventoryCreateUpdateResponseDTO;
import com.codewithkz.inventoryservice.model.Inventory;
import com.codewithkz.inventoryservice.mapper.InventoryMapper;
import com.codewithkz.inventoryservice.service.impl.InventoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/inventories")
public class InventoryController extends BaseController<Inventory, InventoryCreateUpdateRequestDTO, InventoryCreateUpdateResponseDTO, String> {

    private final InventoryServiceImpl service;
    private final InventoryMapper mapper;

    public InventoryController(InventoryServiceImpl service, InventoryMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryCreateUpdateResponseDTO>>> getAll() {
        return super.getAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryCreateUpdateResponseDTO>> create(@RequestBody InventoryCreateUpdateRequestDTO dto) {
        return super.create(dto);
    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<InventoryCreateUpdateResponseDTO>> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @PostMapping("validate")
    public ResponseEntity<ApiResponse<InventoryCreateUpdateResponseDTO>> validateStock(@RequestBody InventoryCreateUpdateRequestDTO dto) {
        var result = this.service.validateStock(dto);
        var responseDTO = this.mapper.toDTO(result);
        return ResponseEntity.ok(ApiResponse.success(responseDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<InventoryCreateUpdateResponseDTO>> update(@PathVariable String id, @RequestBody InventoryCreateUpdateRequestDTO dto) {
        return super.update(id, dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        return super.delete(id);
    }

}
