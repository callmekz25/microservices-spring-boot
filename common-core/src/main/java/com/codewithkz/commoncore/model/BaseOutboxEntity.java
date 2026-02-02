package com.codewithkz.commoncore.model;

import com.codewithkz.commoncore.dto.BaseEntityDTO;
import com.codewithkz.commoncore.enums.OutboxStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseOutboxEntity extends BaseEntityDTO {
    @PrePersist
    public void genId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
    protected String topic;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    protected String payload;
    @Enumerated(EnumType.STRING)
    protected OutboxStatus status = OutboxStatus.PENDING;
    protected int retryCount = 0;
    protected Instant timeRetry = Instant.now();
}
