package com.codewithkz.productservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
import com.codewithkz.productservice.entity.Product;

import java.util.List;

public interface ProductService extends BaseService<Product, Long> {
//    ProductInventoryDto findByIdWithInventory(Long id);
    Product createProduct(ProductCreateUpdateRequestDTO dto);
}
