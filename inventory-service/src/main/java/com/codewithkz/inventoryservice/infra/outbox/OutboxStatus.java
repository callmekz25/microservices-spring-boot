package com.codewithkz.inventoryservice.infra.outbox;

public enum OutboxStatus {
    PENDING,
    COMPLETED,
    FAILED,
    DEAD
}
