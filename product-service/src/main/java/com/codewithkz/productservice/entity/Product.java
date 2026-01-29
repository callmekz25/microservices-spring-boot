package com.codewithkz.productservice.entity;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntityDTO {

    @PrePersist
    public void genId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }

    private String name;
    private int price;
}
