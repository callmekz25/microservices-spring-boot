package com.codewithkz.commoncore.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryReservedEvent {
    private String orderId;
    private String productId;
    private int quantity;
}