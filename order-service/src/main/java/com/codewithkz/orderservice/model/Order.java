package com.codewithkz.orderservice.model;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Order extends BaseEntityDTO {
    private String productId;
    private String userId;
    private int quantity;
    private Double price;
    private Double total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
