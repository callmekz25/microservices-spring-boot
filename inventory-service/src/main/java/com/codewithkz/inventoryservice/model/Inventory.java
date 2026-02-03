package com.codewithkz.inventoryservice.model;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table
@Data
@NoArgsConstructor
@SuperBuilder
public class Inventory extends BaseEntityDTO {
    private String productId;
    private int quantity;
}
