package com.codewithkz.orderservice.entity;

public enum OutboxStatus {
    PENDING,
    COMPLETED,
    FAILED,
    DEAD
}
