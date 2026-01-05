package com.codewithkz.orderservice.infra.rabbitmq.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.orderservice.infra.rabbitmq.event.PaymentCompletedEvent;
import com.codewithkz.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCompletedConsumer {
    private final OrderService service;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_COMPLETED_QUEUE)
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received Payment Completed Event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.COMPLETED);
        log.info("Update Order Payment Completed");
    }
}
