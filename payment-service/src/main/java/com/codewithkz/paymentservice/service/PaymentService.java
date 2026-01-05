package com.codewithkz.paymentservice.service;

import com.codewithkz.paymentservice.core.exception.NotFoundException;
import com.codewithkz.paymentservice.dto.CreatePaymentDto;
import com.codewithkz.paymentservice.dto.PaymentDto;
import com.codewithkz.paymentservice.entity.Payment;
import com.codewithkz.paymentservice.mapper.PaymentMapper;
import com.codewithkz.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;
    private final PaymentMapper mapper;


    public List<PaymentDto> findAll() {
        return mapper.toDtoList(repo.findAll());
    }

    public PaymentDto findByOrderId(Long id) {
        var payment = repo.findByOrderId(id).orElseThrow(() -> new NotFoundException("Payment not found"));

        return mapper.toDto(payment);
    }

    @Transactional
    public void processPayment(CreatePaymentDto dto) {
        var entity = mapper.toEntity(dto);

        repo.save(entity);
    }


}
