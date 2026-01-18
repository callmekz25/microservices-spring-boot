package com.codewithkz.orderservice.proxy;

import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceProxy {

    @GetMapping("/api/products/{id}")
    ApiResponse<ProductDto> getProductById(@PathVariable("id") Long id);

}
