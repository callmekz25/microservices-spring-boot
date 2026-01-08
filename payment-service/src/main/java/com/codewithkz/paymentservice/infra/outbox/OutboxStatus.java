package com.codewithkz.paymentservice.infra.outbox;

public enum OutboxStatus {
    PENDING,
    COMPLETED,
    FAILED,
    DEAD
}
