package com.codewithkz.productservice.model;

import com.codewithkz.commoncore.model.BaseOutboxEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
public class OutboxEvent extends BaseOutboxEntity {
}
