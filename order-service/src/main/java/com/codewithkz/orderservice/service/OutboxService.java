package com.codewithkz.orderservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.orderservice.model.OutboxEvent;

public interface OutboxService extends BaseService<OutboxEvent, String> {
    void create(String topic, Object payload);
}
