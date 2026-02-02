package com.codewithkz.productservice.service;

import com.codewithkz.commoncore.service.BaseService;
import com.codewithkz.productservice.model.OutboxEvent;

public interface OutboxService extends BaseService<OutboxEvent, String> {
    void create(String topic, Object payload);
}
