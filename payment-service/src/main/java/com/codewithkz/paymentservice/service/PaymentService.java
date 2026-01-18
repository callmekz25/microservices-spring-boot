package com.codewithkz.paymentservice.service;

import com.codewithkz.paymentservice.dto.PaymentDto;
import com.codewithkz.paymentservice.event.InventoryReservedEvent;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> findAll();
    PaymentDto findByOrderId(Long id);
    void handleProcessPaymentEvent(InventoryReservedEvent event);
}
