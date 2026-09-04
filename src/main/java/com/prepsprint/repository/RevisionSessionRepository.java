package com.prepsprint.repository;

import com.prepsprint.entity.RevisionSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevisionSessionRepository extends JpaRepository<RevisionSession, Long> {
    List<RevisionSession> findByUserId(Long userId);
}
