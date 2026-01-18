package com.codewithkz.inventoryservice.service;

public interface OutboxService {
    void save(String event, String destination, Object payload);
}
