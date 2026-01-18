package com.codewithkz.orderservice.service;

public interface OutboxService {
    void save(String event, String destination, Object payload);
}
