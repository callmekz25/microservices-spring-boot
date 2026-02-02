package com.codewithkz.paymentservice.model;


import com.codewithkz.commoncore.dto.BaseEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntityDTO {
    private String orderId;
    private Boolean paid;
    private Double amount;
}
