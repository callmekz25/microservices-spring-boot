package com.codewithkz.paymentservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.paymentservice.model.OutboxEvent;

public interface OutboxService extends BaseService<OutboxEvent, String> {
    void create(String topic, Object payload);
}
