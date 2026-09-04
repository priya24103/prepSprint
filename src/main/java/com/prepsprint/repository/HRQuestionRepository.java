package com.prepsprint.repository;

import com.prepsprint.entity.HrQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HRQuestionRepository extends JpaRepository<HrQuestion, Long> {
    List<HrQuestion> findByActiveTrue();
}
