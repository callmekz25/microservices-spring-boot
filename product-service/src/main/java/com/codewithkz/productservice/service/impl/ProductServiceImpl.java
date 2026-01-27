package com.codewithkz.productservice.service.impl;

import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.productservice.proxy.InventoryServiceProxy;
import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.InventoryDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.dto.ProductInventoryDto;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.event.ProductCreatedEvent;
import com.codewithkz.productservice.mapper.ProductMapper;
import com.codewithkz.productservice.repository.ProductRepository;
import com.codewithkz.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;
    private final OutboxServiceImpl outboxService;
    private final InventoryServiceProxy inventoryClient;
    @Value("${app.kafka.topic.create-inventory-command}")
    private String createInventoryTopicName;



    @Override
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ProductDto> finAll() {
        List<Product> entities = repo.findAll();

        return mapper.toDtoList(entities);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ProductDto create(CreateDto dto) {
        Product entity = mapper.toEntity(dto);

        repo.save(entity);

        var payload = ProductCreatedEvent
                .builder()
                .productId(entity.getId())
                .quantity(dto.getQuantity())
                .build();

        outboxService.save(createInventoryTopicName, payload);


        return mapper.toDto(entity);
    }

    @Override
    public ProductDto findById(Long id) {
        Product entity = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));

        return mapper.toDto(entity);
    }

    @Override
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
