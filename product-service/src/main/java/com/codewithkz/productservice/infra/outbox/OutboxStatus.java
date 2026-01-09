package com.codewithkz.productservice.infra.outbox;

public enum OutboxStatus {
    PENDING,
    COMPLETED,
    FAILED,
    DEAD
}
