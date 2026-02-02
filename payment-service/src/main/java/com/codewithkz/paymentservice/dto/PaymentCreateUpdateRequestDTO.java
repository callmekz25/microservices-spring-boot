package com.codewithkz.paymentservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreateUpdateRequestDTO extends BaseDTO {
    private Double amount;
    private Long orderId;
}
