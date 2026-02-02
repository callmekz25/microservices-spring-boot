package com.codewithkz.productservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
import com.codewithkz.productservice.model.Product;

public interface ProductService extends BaseService<Product, String> {
    Product createProduct(ProductCreateUpdateRequestDTO dto);
}
