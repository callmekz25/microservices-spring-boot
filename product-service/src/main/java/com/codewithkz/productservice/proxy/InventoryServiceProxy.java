package com.codewithkz.productservice.proxy;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.productservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryServiceProxy {

    @GetMapping("/api/inventories/products/{id}")
    ApiResponse<InventoryDto> getInventoryByProductId(@PathVariable("id") Long productId);
}
