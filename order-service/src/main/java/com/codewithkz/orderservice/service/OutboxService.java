package com.codewithkz.orderservice.service;

public interface OutboxService {
    void save(String topic, Object payload);
}
