package com.codewithkz.orderservice.mapper;


import com.codewithkz.orderservice.dto.OrderDto;
import com.codewithkz.orderservice.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
    List<OrderDto> toDtoList(List<Order> orders);
}
