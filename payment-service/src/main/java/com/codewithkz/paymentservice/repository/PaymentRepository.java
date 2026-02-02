package com.codewithkz.paymentservice.repository;

import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.paymentservice.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, String> {
    Optional<Payment> findByOrderId(String orderId);
}
