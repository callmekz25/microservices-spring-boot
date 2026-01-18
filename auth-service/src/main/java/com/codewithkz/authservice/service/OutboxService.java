package com.codewithkz.authservice.service;

public interface OutboxService {
    void save(String event, String destination, Object payload);
}
