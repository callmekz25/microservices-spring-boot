package com.codewithkz.productservice.controller;

import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        var result = service.finAll();

        return ResponseEntity.ok(result);
    }
}
