package com.codewithkz.productservice.service;

import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.infra.rabbitmq.event.ProductCreatedEvent;
import com.codewithkz.productservice.infra.rabbitmq.publisher.ProductEventPublisher;
import com.codewithkz.productservice.mapper.ProductMapper;
import com.codewithkz.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;
    private final ProductEventPublisher publisher;

    public ProductService(ProductRepository repo, ProductMapper mapper, ProductEventPublisher publisher) {
        this.repo = repo;
        this.mapper = mapper;
        this.publisher = publisher;
    }

    public List<ProductDto> finAll() {
        List<Product> entities = repo.findAll();

        return mapper.toDtoList(entities);
    }

    @Transactional
    public ProductDto create(CreateDto dto) {
        Product entity = mapper.toEntity(dto);

        repo.save(entity);

        publisher.publishProductCreated(
                new ProductCreatedEvent(entity.getId(), dto.getQuantity())
        );

        return mapper.toDto(entity);
    }


}
