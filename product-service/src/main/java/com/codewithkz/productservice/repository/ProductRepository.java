package com.codewithkz.productservice.repository;

import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}
