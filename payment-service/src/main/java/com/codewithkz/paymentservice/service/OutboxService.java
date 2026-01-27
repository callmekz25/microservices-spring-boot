package com.codewithkz.paymentservice.service;

public interface OutboxService {
    void save(String topic, Object payload);
}
