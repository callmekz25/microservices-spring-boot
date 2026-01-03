package com.codewithkz.productservice.infra.client;

import com.codewithkz.productservice.core.response.ApiResponse;
import com.codewithkz.productservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/api/inventories/products/{id}")
    ApiResponse<InventoryDto> getInventoryByProductId(@PathVariable("id") Long productId);
}
