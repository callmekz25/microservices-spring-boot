package com.codewithkz.paymentservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.paymentservice.model.Payment;
import com.codewithkz.commoncore.event.CreatePaymentEvent;

public interface PaymentService extends BaseService<Payment, String> {
    Payment getByOrderId(String id);
    void handleProcessPaymentEvent(CreatePaymentEvent event);
}
