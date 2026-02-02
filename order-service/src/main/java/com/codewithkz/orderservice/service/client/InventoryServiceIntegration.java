package com.codewithkz.orderservice.service.client;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.InventoryCreateUpdateResponseDTO;
import com.codewithkz.orderservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service")
public interface InventoryServiceIntegration {

    @PostMapping("/api/inventories/validate/{id}")
    ApiResponse<InventoryCreateUpdateResponseDTO> validateStock(@PathVariable String id);
}
