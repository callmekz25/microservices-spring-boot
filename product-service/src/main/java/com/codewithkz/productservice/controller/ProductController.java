package com.codewithkz.productservice.controller;

import com.codewithkz.productservice.core.response.ApiResponse;
import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> findAll() {
        var result = service.finAll();

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> create(@RequestBody CreateDto dto) {
        var result = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));
    }
}
