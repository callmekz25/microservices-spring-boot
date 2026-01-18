package com.codewithkz.orderservice.service.impl;

import com.codewithkz.commoncore.exception.NotFoundException;
import com.codewithkz.commoncore.exception.UnauthorizedException;
import com.codewithkz.orderservice.dto.CreateOrderDto;
import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.dto.ProductDto;
import com.codewithkz.orderservice.entity.Order;
import com.codewithkz.orderservice.entity.OrderStatus;
import com.codewithkz.orderservice.proxy.ProductServiceProxy;
import com.codewithkz.orderservice.config.RabbitMQConfig;
import com.codewithkz.orderservice.event.OrderCreatedEvent;
import com.codewithkz.orderservice.mapper.OrderMapper;
import com.codewithkz.orderservice.repository.OrderRepository;
import com.codewithkz.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;
    private final ProductServiceProxy productServiceProxy;
    private final OutboxServiceImpl outboxService;



    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<OrderDto> findAll() {
        List<Order> orders = repo.findAll();

        return mapper.toDtoList(orders);
    }

    @Override
    @Transactional
    public OrderDto create(CreateOrderDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Unauthenticated");
        }

        Long userId = Long.parseLong(authentication.getName());

        var order = new Order();

        ProductDto product = productServiceProxy.getProductById(dto.getProductId()).getData();

        order.setProductId(product.getId());
        order.setQuantity(dto.getQuantity());
        order.setUserId(userId);
        double total = (double) dto.getQuantity() * product.getPrice();
        order.setPrice((double) product.getPrice());
        order.setTotal(total);
        order.setStatus(OrderStatus.PENDING);

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

    @Override
    @Transactional
    public void updateStatusOrder(Long orderId, OrderStatus status) {
        Order order = repo.findById(orderId).orElseThrow(() -> new NotFoundException("Not found order"));

        order.setStatus(status);
        repo.save(order);
    }




}
