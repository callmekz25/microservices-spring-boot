package com.codewithkz.orderservice.service.impl;

import com.codewithkz.commoncore.exception.UnauthorizedException;
import com.codewithkz.commoncore.response.ApiResponse;
import com.codewithkz.commoncore.service.impl.BaseServiceImpl;
import com.codewithkz.orderservice.dto.InventoryCreateUpdateResponseDTO;
import com.codewithkz.orderservice.dto.PaymentCreateUpdateRequestDTO;
import com.codewithkz.orderservice.dto.ProductCreateUpdateResponseDTO;
import com.codewithkz.orderservice.model.Order;
import com.codewithkz.orderservice.model.OrderStatus;
import com.codewithkz.commoncore.event.CreatePaymentEvent;
import com.codewithkz.commoncore.event.InventoryReservedEvent;
import com.codewithkz.orderservice.service.client.InventoryServiceIntegration;
import com.codewithkz.orderservice.service.client.PaymentServiceIntegration;
import com.codewithkz.orderservice.service.client.ProductServiceIntegration;
import com.codewithkz.commoncore.event.OrderCreatedEvent;
import com.codewithkz.orderservice.repository.OrderRepository;
import com.codewithkz.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    private final ProductServiceIntegration productServiceIntegration;
    private final InventoryServiceIntegration inventoryServiceIntegration;
    private final PaymentServiceIntegration paymentServiceIntegration;
    private final OrderRepository orderRepository;
    private final OutboxServiceImpl outboxService;
    @Value("${app.kafka.topic.reserve-inventory-command}")
    private String reserveInventoryTopicName;
    @Value("${app.kafka.topic.create-payment-command}")
    private String createPaymentTopicName;

    public OrderServiceImpl(OrderRepository repository, ProductServiceIntegration productServiceIntegration, OutboxServiceImpl outboxService,
                            InventoryServiceIntegration inventoryServiceIntegration, PaymentServiceIntegration paymentServiceIntegration) {
        super(repository);
        this.orderRepository = repository;
        this.outboxService = outboxService;
        this.productServiceIntegration = productServiceIntegration;
        this.inventoryServiceIntegration = inventoryServiceIntegration;
        this.paymentServiceIntegration = paymentServiceIntegration;
    }

    @Override
    @Transactional
    public Order create(Order dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthenticated");
        }

        String userId = authentication.getName();

        var order = new Order();

        ProductCreateUpdateResponseDTO product = productServiceIntegration.getById(dto.getProductId()).getData();
        inventoryServiceIntegration.validateStock(product.getId());

        order.setProductId(product.getId());
        order.setQuantity(dto.getQuantity());
        order.setUserId(userId);
        double total = (double) dto.getQuantity() * product.getPrice();
        order.setPrice((double) product.getPrice());
        order.setTotal(total);
        order.setStatus(OrderStatus.PENDING);

        Order created = repository.save(order);

        PaymentCreateUpdateRequestDTO request = new PaymentCreateUpdateRequestDTO(created.getTotal(), created.getId());
        paymentServiceIntegration.create(request);

        created.setStatus(OrderStatus.COMPLETED);

        return created;



//        OrderCreatedEvent payload = OrderCreatedEvent
//                .builder()
//                .orderId(order.getId())
//                .productId(order.getProductId())
//                .quantity(order.getQuantity())
//                .userId(order.getUserId())
//                .price(order.getPrice())
//                .total(order.getTotal())
//                .build();

//        outboxService.create(reserveInventoryTopicName, payload);

    }

    @Override
    @Transactional
    public void updateStatusOrder(String orderId, OrderStatus status) {
        Order order = getById(orderId);

        order.setStatus(status);
        super.update(orderId, order);
    }

    @Override
    @Transactional
    public void handleCreatePaymentCommand(InventoryReservedEvent event) {
        Order order = getById(event.getOrderId());

        CreatePaymentEvent payload = CreatePaymentEvent
                .builder()
                .orderId(order.getId())
                .amount(order.getTotal())
                .build();

        outboxService.create(createPaymentTopicName, payload);
    }

}
