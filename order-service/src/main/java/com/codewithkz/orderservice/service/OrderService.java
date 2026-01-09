package com.codewithkz.orderservice.service;

import com.codewithkz.orderservice.core.exception.NotFoundException;
import com.codewithkz.orderservice.dto.CreateOrderDto;
import com.codewithkz.orderservice.dto.InventoryDto;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.dto.ProductDto;
import com.codewithkz.orderservice.entity.Order;
import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.infra.client.InventoryClient;
import com.codewithkz.orderservice.infra.client.ProductClient;
import com.codewithkz.orderservice.infra.outbox.OutboxService;
import com.codewithkz.orderservice.infra.rabbitmq.config.RabbitMQConfig;
import com.codewithkz.orderservice.infra.rabbitmq.event.OrderCreatedEvent;
import com.codewithkz.orderservice.infra.rabbitmq.publisher.OrderEventPublisher;
import com.codewithkz.orderservice.mapper.OrderMapper;
import com.codewithkz.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final ProductClient productClient;
    private final OrderEventPublisher publisher;
    private final OutboxService outboxService;



    public List<OrderDto> findAll() {
        List<Order> orders = repo.findAll();

        return mapper.toDtoList(orders);
    }

    @Transactional
    public OrderDto create(CreateOrderDto dto) {

        var order = new Order();

        ProductDto product = productClient.getProductById(dto.getProductId()).getData();

        order.setProductId(product.getId());
        order.setQuantity(dto.getQuantity());
        order.setUserId(dto.getUserId());
        double total = (double) dto.getQuantity() * product.getPrice();
        order.setPrice((double) product.getPrice());
        order.setTotal(total);
        order.setStatus(OrderStatus.PENDING);
        order.setUserId(dto.getUserId());

        repo.save(order);


        OrderCreatedEvent event = OrderCreatedEvent
                .builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .userId(order.getUserId())
                .price(order.getPrice())
                .total(order.getTotal())
                .build();

        outboxService.save(RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, RabbitMQConfig.ORDER_CREATED_ROUTING_KEY, event);


        return mapper.toDto(order);

    }

    @Transactional
    public void updateStatusOrder(Long orderId, OrderStatus status) {
        Order order = repo.findById(orderId).orElseThrow(() -> new NotFoundException("Not found order"));

        order.setStatus(status);
        repo.save(order);
    }




}
