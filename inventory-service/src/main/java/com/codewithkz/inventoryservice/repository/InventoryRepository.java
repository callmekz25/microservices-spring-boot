package com.codewithkz.inventoryservice.repository;

import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends BaseRepository<Inventory, String> {
    Optional<Inventory> getByProductId(String productId);


    @Modifying
    @Query("update Inventory i set i.quantity = i.quantity - :quantity where i.productId = :productId and i.quantity >= :quantity")
    int decreaseQuantity(String productId, int quantity);

    @Modifying
    @Query("update Inventory i set i.quantity = i.quantity + :quantity where i.productId = :productId")
    int increaseQuantity(String productId, int quantity);
}
