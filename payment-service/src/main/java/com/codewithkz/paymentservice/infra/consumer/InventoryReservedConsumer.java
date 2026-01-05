package com.codewithkz.paymentservice.infra.consumer;

import com.codewithkz.paymentservice.dto.CreatePaymentDto;
import com.codewithkz.paymentservice.infra.config.RabbitMQConfig;
import com.codewithkz.paymentservice.infra.event.InventoryReservedEvent;
import com.codewithkz.paymentservice.infra.event.PaymentCompletedEvent;
import com.codewithkz.paymentservice.infra.event.PaymentFailedEvent;
import com.codewithkz.paymentservice.infra.publisher.PaymentEventPublisher;
import com.codewithkz.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryReservedConsumer {
    private final PaymentService service;
    private final PaymentEventPublisher publisher;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RESERVED_QUEUE)
    public void handleInventoryReserved(InventoryReservedEvent event) {
        log.info("Received InventoryReserved event: {}", event.getOrderId());

        try {
            var createPayment = CreatePaymentDto.builder()
                    .orderId(event.getOrderId()).amount(event.getAmount()).build();

            service.processPayment(createPayment);

            log.info("Payment processed: {}", createPayment.getOrderId());

            var paymentCompletedEvent = PaymentCompletedEvent.builder()
                    .orderId(event.getOrderId()).build();

            publisher.publishPaymentCompleted(paymentCompletedEvent);

        }catch (Exception e) {
            log.error("Payment failed for Order: {}. Reason: {}", event.getOrderId(), e.getMessage());

            var paymentFailedEvent = PaymentFailedEvent.builder()
                    .orderId(event.getOrderId())
                    .productId(event.getProductId())
                    .quantity(event.getQuantity())
                    .reason(e.getMessage())
                    .build();

            publisher.publishPaymentFailed(paymentFailedEvent);
        }



    }
}
