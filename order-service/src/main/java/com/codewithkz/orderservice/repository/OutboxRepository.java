package com.codewithkz.orderservice.repository;

import com.codewithkz.commoncore.repository.BaseRepository;
import com.codewithkz.orderservice.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OutboxRepository extends BaseRepository<OutboxEvent, String> {
    @Query("""
    SELECT e FROM OutboxEvent e
    WHERE (e.status = 'PENDING'
       OR (e.status = 'FAILED' AND e.timeRetry <= :now))
    ORDER BY e.createdAt
    LIMIT 50
    """)
    List<OutboxEvent> findReadyToPublish(Instant now);

}
