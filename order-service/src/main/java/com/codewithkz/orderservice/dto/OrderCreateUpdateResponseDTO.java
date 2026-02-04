package com.codewithkz.orderservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import com.codewithkz.orderservice.model.OrderStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderCreateUpdateResponseDTO extends BaseDTO {
    private String productId;
    private int quantity;
    private Double price;
    private Double total;
    private OrderStatus status;
}