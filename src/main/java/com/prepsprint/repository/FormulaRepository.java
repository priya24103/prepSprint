package com.prepsprint.repository;

import com.prepsprint.entity.Formula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormulaRepository extends JpaRepository<Formula, Long> {
    List<Formula> findByTopicIdAndActiveTrue(Long topicId);
    List<Formula> findByActiveTrue();
}
