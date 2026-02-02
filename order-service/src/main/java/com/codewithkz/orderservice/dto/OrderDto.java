package com.codewithkz.orderservice.dto;

import com.codewithkz.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private Long productId;
    private String userId;
    private int quantity;
    private Double price;
    private Double total;
    private OrderStatus status;
}
