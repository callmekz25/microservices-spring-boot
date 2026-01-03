package com.codewithkz.productservice.service;

import com.codewithkz.productservice.infra.client.InventoryClient;
import com.codewithkz.productservice.core.exception.NotFoundException;
import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.InventoryDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.dto.ProductInventoryDto;
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
    private final InventoryClient inventoryClient;

    public ProductService(ProductRepository repo, ProductMapper mapper, ProductEventPublisher publisher, InventoryClient inventoryClient) {
        this.repo = repo;
        this.mapper = mapper;
        this.publisher = publisher;
        this.inventoryClient = inventoryClient;
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

    public ProductDto findById(Long id) {
        Product entity = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        return mapper.toDto(entity);
    }

    public ProductInventoryDto findByIdWithInventory(Long id) {
        Product product = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        ProductDto dto = mapper.toDto(product);

        InventoryDto inventoryDto = inventoryClient.getInventoryByProductId(dto.getId()).getData();

        return ProductInventoryDto
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(inventoryDto.getQuantity())
                .build();
    }


}
