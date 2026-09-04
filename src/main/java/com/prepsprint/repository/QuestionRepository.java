package com.prepsprint.repository;

import com.prepsprint.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopicIdAndActiveTrue(Long topicId);
    List<Question> findByTopicSubjectIdAndActiveTrue(Long subjectId);
    List<Question> findByTopicIdInAndActiveTrue(List<Long> topicIds);
    List<Question> findByIdInAndActiveTrue(List<Long> ids);
    List<Question> findByActiveTrue();
}
