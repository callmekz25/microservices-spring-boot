package com.codewithkz.productservice.service;

public interface OutboxService {
    void save(String event, String destination, Object payload);
}
