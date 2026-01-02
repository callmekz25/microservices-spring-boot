package com.codewithkz.productservice.service;

import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.mapper.ProductMapper;
import com.codewithkz.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repo, ProductMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ProductDto> finAll() {
        List<Product> entities = repo.findAll();

        return mapper.toDtoList(entities);
    }


}
