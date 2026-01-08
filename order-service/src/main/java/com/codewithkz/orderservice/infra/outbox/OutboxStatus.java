package com.codewithkz.orderservice.infra.outbox;

public enum OutboxStatus {
    PENDING,
    COMPLETED,
    FAILED,
    DEAD
}
