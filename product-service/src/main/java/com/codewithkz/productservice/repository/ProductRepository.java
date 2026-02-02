package com.codewithkz.productservice.repository;

import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.productservice.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, String> {
}
