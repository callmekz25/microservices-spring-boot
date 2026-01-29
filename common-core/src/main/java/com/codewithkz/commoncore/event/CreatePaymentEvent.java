package com.codewithkz.commoncore.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentEvent {
    private Long orderId;
    private Double amount;
}
