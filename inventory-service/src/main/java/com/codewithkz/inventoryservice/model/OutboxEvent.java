package com.codewithkz.inventoryservice.model;

import com.codewithkz.commoncore.model.BaseOutboxEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class OutboxEvent extends BaseOutboxEntity {
}