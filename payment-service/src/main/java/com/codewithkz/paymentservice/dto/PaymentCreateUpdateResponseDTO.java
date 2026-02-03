package com.codewithkz.paymentservice.dto;

import com.codewithkz.commoncore.dto.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaymentCreateUpdateResponseDTO extends BaseDTO {
    private Double amount;
    private boolean paid;
    private Long orderId;
}
