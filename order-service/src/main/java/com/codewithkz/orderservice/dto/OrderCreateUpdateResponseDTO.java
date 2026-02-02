package com.codewithkz.orderservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import com.codewithkz.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreateUpdateResponseDTO extends BaseDTO {
    private Long productId;
    private int quantity;
    private Double price;
    private Double total;
    private OrderStatus status;
}