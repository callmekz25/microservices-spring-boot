package com.codewithkz.productservice.service;

import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.productservice.infra.client.InventoryClient;
import com.codewithkz.productservice.dto.CreateDto;
import com.codewithkz.productservice.dto.InventoryDto;
import com.codewithkz.productservice.dto.ProductDto;
import com.codewithkz.productservice.dto.ProductInventoryDto;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.infra.outbox.OutboxService;
import com.codewithkz.productservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.productservice.infra.rabbitmq.event.ProductCreatedEvent;
import com.codewithkz.productservice.mapper.ProductMapper;
import com.codewithkz.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;
    private final OutboxService outboxService;
    private final InventoryClient inventoryClient;



//    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public List<ProductDto> finAll() {
        List<Product> entities = repo.findAll();

        return mapper.toDtoList(entities);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Transactional
    public ProductDto create(CreateDto dto) {
        Product entity = mapper.toEntity(dto);

        repo.save(entity);

        var event = ProductCreatedEvent
                .builder()
                .productId(entity.getId())
                .quantity(dto.getQuantity())
                .build();

        outboxService.save(RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY,
                RabbitMQConfig.PRODUCT_CREATED_ROUTING_KEY,
                event);


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
