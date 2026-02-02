package com.codewithkz.orderservice.service.client;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.ProductCreateUpdateResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceIntegration {

    @GetMapping("/api/products/{id}")
    ApiResponse<ProductCreateUpdateResponseDTO> getById(@PathVariable("id") String id);

}
