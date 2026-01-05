package com.codewithkz.orderservice.infra.rabbitmq.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryReleasedEvent {
    private Long orderId;
}
