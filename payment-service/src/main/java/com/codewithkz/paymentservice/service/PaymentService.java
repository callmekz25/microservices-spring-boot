package com.codewithkz.paymentservice.service;

import com.codewithkz.paymentservice.core.exception.NotFoundException;
import com.codewithkz.paymentservice.dto.CreatePaymentDto;
import com.codewithkz.paymentservice.dto.PaymentDto;
import com.codewithkz.paymentservice.entity.Payment;
import com.codewithkz.paymentservice.infra.config.RabbitMQConfig;
import com.codewithkz.paymentservice.infra.event.InventoryReservedEvent;
import com.codewithkz.paymentservice.infra.event.PaymentCompletedEvent;
import com.codewithkz.paymentservice.infra.event.PaymentFailedEvent;
import com.codewithkz.paymentservice.infra.outbox.OutboxService;
import com.codewithkz.paymentservice.mapper.PaymentMapper;
import com.codewithkz.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;
    private final PaymentMapper mapper;
    private final OutboxService outboxService;


    public List<PaymentDto> findAll() {
        return mapper.toDtoList(repo.findAll());
    }

    public PaymentDto findByOrderId(Long id) {
        var payment = repo.findByOrderId(id).orElseThrow(() -> new NotFoundException("Payment not found"));

        return mapper.toDto(payment);
    }

    @Transactional
    public void handleProcessPaymentEvent(InventoryReservedEvent event) {

        try {
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



        }catch (Exception e) {
            log.error("Payment failed for Order: {}. Reason: {}", event.getOrderId(), e.getMessage());

            var paymentFailedEvent = PaymentFailedEvent.builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .reason(e.getMessage())
                    .build();

            outboxService.save(RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY,
                    RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY,
                    paymentFailedEvent);

        }
    }


}
