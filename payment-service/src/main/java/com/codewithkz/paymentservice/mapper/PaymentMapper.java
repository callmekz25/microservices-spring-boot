package com.codewithkz.paymentservice.mapper;

import com.codewithkz.commoncore.mapper.BaseMapper;
import com.codewithkz.paymentservice.dto.PaymentCreateUpdateRequestDTO;
import com.codewithkz.paymentservice.dto.PaymentCreateUpdateResponseDTO;
import com.codewithkz.paymentservice.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<Payment, PaymentCreateUpdateRequestDTO, PaymentCreateUpdateResponseDTO> {

}
