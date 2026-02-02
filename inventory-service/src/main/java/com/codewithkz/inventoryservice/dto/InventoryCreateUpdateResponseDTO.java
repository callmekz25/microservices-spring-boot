package com.codewithkz.inventoryservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryCreateUpdateResponseDTO extends BaseDTO {
    private String productId;
    private int quantity;
}

