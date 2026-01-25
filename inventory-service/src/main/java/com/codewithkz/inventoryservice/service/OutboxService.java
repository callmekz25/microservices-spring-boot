package com.codewithkz.inventoryservice.service;

public interface OutboxService {
    void save(String topic, Object payload);
}
