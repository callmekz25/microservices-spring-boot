package com.codewithkz.paymentservice.service.impl;


import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.paymentservice.dto.CreatePaymentDto;
import com.codewithkz.paymentservice.dto.PaymentDto;
import com.codewithkz.paymentservice.config.RabbitMQConfig;
import com.codewithkz.paymentservice.event.InventoryReservedEvent;
import com.codewithkz.paymentservice.event.PaymentCompletedEvent;
import com.codewithkz.paymentservice.mapper.PaymentMapper;
import com.codewithkz.paymentservice.repository.PaymentRepository;
import com.codewithkz.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repo;
    private final PaymentMapper mapper;
    private final OutboxServiceImpl outboxService;


    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<PaymentDto> findAll() {
        return mapper.toDtoList(repo.findAll());
    }


    @Override
    public PaymentDto findByOrderId(Long id) {
        var payment = repo.findByOrderId(id).orElseThrow(() -> new NotFoundException("Payment not found"));

        return mapper.toDto(payment);
    }

    @Override
    @Transactional
    public void handleProcessPaymentEvent(InventoryReservedEvent event) {


        var createPayment = CreatePaymentDto.builder()
                    .orderId(event.getOrderId()).amount(event.getAmount()).build();

        var entity = mapper.toEntity(createPayment);

        repo.save(entity);

        log.info("Payment processed: {}", createPayment.getOrderId());

        var paymentCompletedEvent = PaymentCompletedEvent.builder()
                    .orderId(event.getOrderId()).build();

        outboxService.save(RabbitMQConfig.PAYMENT_COMPLETED_ROUTING_KEY,
                    RabbitMQConfig.PAYMENT_COMPLETED_ROUTING_KEY,
                    paymentCompletedEvent);


//        log.error("Payment failed for Order: {}. Reason: {}", event.getOrderId(), e.getMessage());
//
//        var paymentFailedEvent = PaymentFailedEvent.builder()
//                .orderId(event.getOrderId())
//                .productId(event.getProductId())
//                .quantity(event.getQuantity())
//                .reason("Not enough money")
//                .build();
//
//        outboxService.save(RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY,
//                RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY,
//                paymentFailedEvent);
    }


}
