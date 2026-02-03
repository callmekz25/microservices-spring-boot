package com.codewithkz.productservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductCreateUpdateRequestDTO extends BaseDTO {
    private String name;
    private int price;
    private int quantity;
}
