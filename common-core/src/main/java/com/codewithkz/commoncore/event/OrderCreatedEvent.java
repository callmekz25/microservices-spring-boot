package com.codewithkz.commoncore.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedEvent {
    private String orderId;
    private Double price;
    private String productId;
    private Double total;
    private int quantity;
    private String userId;
}
