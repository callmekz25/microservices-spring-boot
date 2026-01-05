package com.codewithkz.inventoryservice.service;


import com.codewithkz.inventoryservice.core.exception.NotFoundException;
import com.codewithkz.inventoryservice.dto.InventoryDto;
import com.codewithkz.inventoryservice.entity.Inventory;
import com.codewithkz.inventoryservice.mapper.InventoryMapper;
import com.codewithkz.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repo;
    private final InventoryMapper mapper;

    public InventoryService(InventoryRepository repo, InventoryMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<InventoryDto> findAll() {
        List<Inventory> inventories = repo.findAll();

        return mapper.toDtoList(inventories);
    }

    public void create(Long productId, int quantity) {
        Inventory inventory = Inventory.builder().productId(productId).quantity(quantity).build();

        repo.save(inventory);
    }

    public InventoryDto findByProductId(Long id) {
        Inventory inventory = repo.findByProductId(id).orElseThrow(() -> new NotFoundException("Inventory not found"));

        return mapper.toDto(inventory);
    }

    @Transactional
    public boolean reserved(Long productId, int quantity) {
       int update = repo.decreaseQuantity(productId, quantity);
       return update == 1;
    }

    @Transactional
    public boolean released(Long productId, int quantity) {
        int update = repo.increaseQuantity(productId, quantity);
        return update == 1;
    }

}
