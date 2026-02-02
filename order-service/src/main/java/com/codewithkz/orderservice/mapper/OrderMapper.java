package com.codewithkz.orderservice.mapper;


import com.codewithkz.commoncore.mapper.BaseMapper;
import com.codewithkz.orderservice.dto.OrderCreateUpdateRequestDTO;
import com.codewithkz.orderservice.dto.OrderCreateUpdateResponseDTO;
import com.codewithkz.orderservice.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderCreateUpdateRequestDTO, OrderCreateUpdateResponseDTO> {
}
