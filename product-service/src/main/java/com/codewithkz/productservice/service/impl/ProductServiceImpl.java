package com.codewithkz.productservice.service.impl;

import com.codewithkz.commoncore.event.ProductCreatedEvent;
import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
import com.codewithkz.productservice.service.client.InventoryServiceIntegration;
import com.codewithkz.productservice.model.Product;
import com.codewithkz.productservice.repository.ProductRepository;
import com.codewithkz.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {

    private final ProductRepository repo;
    private final OutboxServiceImpl outboxService;
    @Value("${app.kafka.topic.create-inventory-command}")
    private String createInventoryTopicName;

    public ProductServiceImpl(ProductRepository repo,  OutboxServiceImpl outboxService, InventoryServiceIntegration inventoryClient) {
        super(repo);
        this.repo = repo;
        this.outboxService = outboxService;
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @Override
    public Product createProduct(ProductCreateUpdateRequestDTO dto) {
        Product entity = Product
                .builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .build();

        Product created = super.create(entity);

        // Send to Inventory Service
        var payload = ProductCreatedEvent
                .builder()
                .productId(created.getId())
                .quantity(dto.getQuantity())
                .build();

        outboxService.create(createInventoryTopicName, payload);
        return created;
    }

}
