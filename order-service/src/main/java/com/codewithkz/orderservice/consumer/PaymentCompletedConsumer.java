package com.codewithkz.orderservice.consumer;

import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.config.RabbitMQConfig;
import com.codewithkz.orderservice.event.PaymentCompletedEvent;
import com.codewithkz.orderservice.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCompletedConsumer {
    private final OrderServiceImpl service;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_COMPLETED_QUEUE)
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received Payment Completed Event: {}", event.getOrderId());

        service.updateStatusOrder(event.getOrderId(), OrderStatus.COMPLETED);
        log.info("Update Order Payment Completed");
    }
}
