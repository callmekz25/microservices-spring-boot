package com.codewithkz.paymentservice.service.impl;


import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.paymentservice.dto.PaymentCreateUpdateRequestDTO;
import com.codewithkz.paymentservice.model.Payment;
import com.codewithkz.paymentservice.event.CreatePaymentEvent;
import com.codewithkz.paymentservice.event.PaymentCompletedEvent;
import com.codewithkz.paymentservice.mapper.PaymentMapper;
import com.codewithkz.paymentservice.repository.PaymentRepository;
import com.codewithkz.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PaymentServiceImpl extends BaseServiceImpl<Payment, String> implements PaymentService {

    private final PaymentRepository repo;
    private final PaymentMapper mapper;
    private final OutboxServiceImpl outboxService;
    @Value("${app.kafka.topic.payment-completed}")
    private String paymentCompletedTopicName;

    public PaymentServiceImpl(PaymentRepository repository, PaymentMapper mapper, OutboxServiceImpl outboxService) {
        super(repository);
        this.repo = repository;
        this.mapper = mapper;
        this.outboxService = outboxService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Override
    public List<Payment> getAll() {
        return repo.findAll();
    }


    @Override
    public Payment getByOrderId(String id) {
        return repo.findByOrderId(id).orElseThrow(() -> new NotFoundException("Payment not found"));
    }

    @Override
    public Payment create(Payment entity) {
        entity.setPaid(true);
        return repo.save(entity);
    }

    @Override
    @Transactional
    public void handleProcessPaymentEvent(CreatePaymentEvent event) {


        var createPayment = PaymentCreateUpdateRequestDTO.builder()
                    .orderId(event.getOrderId()).amount(event.getAmount()).build();

        var entity = mapper.toEntity(createPayment);
        entity.setPaid(true);

        repo.save(entity);

        log.info("Payment processed: {}", createPayment.getOrderId());

        PaymentCompletedEvent payload = PaymentCompletedEvent.builder()
                    .orderId(event.getOrderId()).build();

        outboxService.create(paymentCompletedTopicName, payload);

    }


}
