package com.codewithkz.orderservice.repository;

import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.orderservice.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, String> {
}
