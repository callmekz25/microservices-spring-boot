package com.codewithkz.orderservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreateUpdateResponseDTO extends BaseDTO {
    private Double amount;
    private boolean paid;
    private Long orderId;
}
