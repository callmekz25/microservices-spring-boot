package com.codewithkz.inventoryservice.model;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory extends BaseEntityDTO {
    private String productId;
    private int quantity;
}
