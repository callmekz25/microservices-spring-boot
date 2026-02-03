package com.codewithkz.orderservice.service.client;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.InventoryCreateUpdateRequestDTO;
import com.codewithkz.orderservice.dto.InventoryCreateUpdateResponseDTO;
import com.codewithkz.orderservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface InventoryServiceIntegration {

    @PostMapping("/api/inventories/validate")
    ApiResponse<InventoryCreateUpdateResponseDTO> validateStock(@RequestBody InventoryCreateUpdateRequestDTO dto);
}
