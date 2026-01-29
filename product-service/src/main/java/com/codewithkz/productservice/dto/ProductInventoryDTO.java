package com.codewithkz.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInventoryDTO {
    private Long id;
    private String name;
    private int price;
    private int quantity;
}
