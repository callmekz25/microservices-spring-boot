package com.codewithkz.productservice.model;

import com.codewithkz.commoncore.model.BaseOutboxEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table
@Data
@SuperBuilder
@NoArgsConstructor
public class OutboxEvent extends BaseOutboxEntity {
}
