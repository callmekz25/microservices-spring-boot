package com.codewithkz.paymentservice.service;

public interface OutboxService {
    void save(String event, String destination, Object payload);
}
