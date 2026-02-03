package com.codewithkz.paymentservice.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentEvent {
    private String orderId;
    private Double amount;
}
