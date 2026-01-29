package com.codewithkz.productservice.service.impl;

import com.codewithkz.commoncore.event.ProductCreatedEvent;
import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.productservice.dto.ProductCreateUpdateRequestDTO;
import com.codewithkz.productservice.proxy.InventoryServiceProxy;
import com.codewithkz.productservice.entity.Product;
import com.codewithkz.productservice.repository.ProductRepository;
import com.codewithkz.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService {

    private final ProductRepository repo;
    private final OutboxServiceImpl outboxService;
    private final InventoryServiceProxy inventoryClient;
    @Value("${app.kafka.topic.create-inventory-command}")
    private String createInventoryTopicName;

    public ProductServiceImpl(ProductRepository repo,  OutboxServiceImpl outboxService, InventoryServiceProxy inventoryClient) {
        super(repo);
        this.repo = repo;
        this.outboxService = outboxService;
        this.inventoryClient = inventoryClient;
    }



    @Override
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<Product> getAll() {
        return super.getAll();
    }

    @Override
    public Product create(Product source) {
        return super.create(source);
    }

    @Override
    public Product getById(Long id) {
        return super.getById(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Override
    public Product createProduct(ProductCreateUpdateRequestDTO dto) {
        Product entity = new Product(dto.getName(), dto.getPrice());

        var payload = ProductCreatedEvent
                .builder()
                .productId(entity.getId())
                .quantity(dto.getQuantity())
                .build();

        outboxService.save(createInventoryTopicName, payload);
        return entity;
    }

//    @Override
//    public ProductInventoryDto findByIdWithInventory(Long id) {
//        Product product = repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
//
//        ProductDto dto = mapper.toDto(product);
//
//        InventoryDto inventoryDto = inventoryClient.getInventoryByProductId(dto.getId()).getData();
//
//        return ProductInventoryDto
//                .builder()
//                .id(dto.getId())
//                .name(dto.getName())
//                .price(dto.getPrice())
//                .quantity(inventoryDto.getQuantity())
//                .build();
//    }


}
