package com.codewithkz.inventoryservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class InventoryCreateUpdateRequestDTO extends BaseDTO {
    private String productId;
    private int quantity;
}
