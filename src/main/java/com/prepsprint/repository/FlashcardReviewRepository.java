package com.prepsprint.repository;

import com.prepsprint.entity.FlashcardReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlashcardReviewRepository extends JpaRepository<FlashcardReview, Long> {
    Optional<FlashcardReview> findByUserIdAndFlashcardId(Long userId, Long flashcardId);
    List<FlashcardReview> findByUserId(Long userId);
}
