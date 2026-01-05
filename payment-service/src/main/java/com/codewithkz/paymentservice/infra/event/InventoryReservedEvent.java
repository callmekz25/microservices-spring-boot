package com.codewithkz.paymentservice.infra.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryReservedEvent {
    private Long orderId;
    private Long productId;
    private int quantity;
    private Double amount;
}