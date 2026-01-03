package com.codewithkz.inventoryservice.repository;

import com.codewithkz.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(Long productId);


    @Modifying
    @Query("update Inventory i set i.quantity = i.quantity - :quantity where i.productId = :productId and i.quantity >= :quantity")
    int decreaseQuantity(Long productId, int quantity);

    @Modifying
    @Query("update Inventory i set i.quantity = i.quantity + :quantity where i.productId = :productId and i.quantity >= :quantity")
    int increaseQuantity(Long productId, int quantity);
}
