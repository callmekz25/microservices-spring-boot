package com.codewithkz.productservice.controller;

import com.codewithkz.commoncore.controller.BaseController;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
import com.codewithkz.productservice.dto.ProductCreateUpdateResponseDTO;
import com.codewithkz.productservice.model.Product;
import com.codewithkz.productservice.mapper.ProductMapper;
import com.codewithkz.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController extends BaseController<Product, ProductCreateUpdateRequestDTO, ProductCreateUpdateResponseDTO, String> {

    private final ProductMapper productMapper;
    private final ProductService productService;

    public ProductController(ProductService service, ProductMapper productMapper) {
        super(service, productMapper);
        this.productMapper = productMapper;
        this.productService = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCreateUpdateResponseDTO>>> getAll() {
        return super.getAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductCreateUpdateResponseDTO>> create(@RequestBody ProductCreateUpdateRequestDTO dto) {
        Product created = productService.createProduct(dto);
        ProductCreateUpdateResponseDTO responseDTO = productMapper.toDTO(created);
        return ResponseEntity.ok(ApiResponse.success(responseDTO));
    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<ProductCreateUpdateResponseDTO>> getById(@PathVariable String id) {
        return super.getById(id);
    }
}
