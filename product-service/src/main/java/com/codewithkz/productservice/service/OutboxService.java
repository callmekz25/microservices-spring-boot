package com.codewithkz.productservice.service;

public interface OutboxService {
    void save(String topic, Object payload);
}
