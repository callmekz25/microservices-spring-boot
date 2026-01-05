package com.codewithkz.paymentservice.mapper;

import com.codewithkz.paymentservice.dto.CreatePaymentDto;
import com.codewithkz.paymentservice.dto.PaymentDto;
import com.codewithkz.paymentservice.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
    Payment toEntity(CreatePaymentDto dto);
    List<PaymentDto> toDtoList(List<Payment> payments);
}
