package com.codewithkz.productservice.service;

import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.dto.ProductInventoryDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> finAll();
    ProductDto create(CreateDto dto);
    ProductDto findById(Long id);
    ProductInventoryDto findByIdWithInventory(Long id);
}
