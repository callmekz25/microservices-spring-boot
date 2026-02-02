package com.codewithkz.productservice.service.client;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.productservice.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryServiceIntegration {

    @GetMapping("/api/inventories/products/{id}")
    ApiResponse<InventoryDTO> getInventoryByProductId(@PathVariable("id") Long productId);
}
