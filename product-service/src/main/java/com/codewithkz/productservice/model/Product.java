package com.codewithkz.productservice.model;

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
public class Product extends BaseEntityDTO {
    private String name;
    private int price;
}
