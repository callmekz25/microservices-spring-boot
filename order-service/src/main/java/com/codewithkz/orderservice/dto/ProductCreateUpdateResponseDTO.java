package com.codewithkz.orderservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateUpdateResponseDTO extends BaseDTO {
    private String name;
    private int price;
    private int quantity;
}
