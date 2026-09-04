package com.prepsprint.repository;

import com.prepsprint.entity.SessionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionItemRepository extends JpaRepository<SessionItem, Long> {
    List<SessionItem> findByRevisionSessionId(Long sessionId);
}
