package com.codewithkz.orderservice.model;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends BaseEntityDTO {
    private String productId;
    private String userId;
    private int quantity;
    private Double price;
    private Double total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
