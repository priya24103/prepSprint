package com.prepsprint.repository;

import com.prepsprint.entity.TechnicalQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalQuestionRepository extends JpaRepository<TechnicalQuestion, Long> {
    List<TechnicalQuestion> findByActiveTrue();
    List<TechnicalQuestion> findByTopicIdAndActiveTrue(Long topicId);
}
