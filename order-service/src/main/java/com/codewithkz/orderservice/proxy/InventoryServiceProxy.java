package com.codewithkz.orderservice.proxy;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryServiceProxy {

    @GetMapping("/api/inventories/{id}")
    ApiResponse<InventoryDto> getInventoryByProductId(@PathVariable Long id);
}
