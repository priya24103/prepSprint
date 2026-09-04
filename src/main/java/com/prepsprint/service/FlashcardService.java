package com.prepsprint.service;

import com.prepsprint.dto.FlashcardDto;
import com.prepsprint.entity.*;
import com.prepsprint.enums.ActivityType;
import com.prepsprint.enums.ContentType;
import com.prepsprint.enums.FlashcardRating;
import com.prepsprint.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final FlashcardReviewRepository flashcardReviewRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;

    public FlashcardService(FlashcardRepository flashcardRepository,
                            FlashcardReviewRepository flashcardReviewRepository,
                            BookmarkRepository bookmarkRepository,
                            UserRepository userRepository,
                            ActivityLogRepository activityLogRepository) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardReviewRepository = flashcardReviewRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
    }

    @Transactional(readOnly = true)
    public FlashcardDto getFlashcardDetail(Long userId, Long flashcardId) {
        Flashcard card = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found with id: " + flashcardId));

        Long topicId = card.getTopic().getId();
        List<Flashcard> topicCards = flashcardRepository.findByTopicIdAndActiveTrue(topicId);

        int totalInTopic = topicCards.size();
        int currentIndex = 1;
        Long prevId = null;
        Long nextId = null;

        for (int i = 0; i < topicCards.size(); i++) {
            if (topicCards.get(i).getId().equals(flashcardId)) {
                currentIndex = i + 1;
                if (i > 0) {
                    prevId = topicCards.get(i - 1).getId();
                }
                if (i < topicCards.size() - 1) {
                    nextId = topicCards.get(i + 1).getId();
                }
                break;
            }
        }

        int currentBoxLevel = 1;
        if (userId != null) {
            Optional<FlashcardReview> reviewOpt = flashcardReviewRepository.findByUserIdAndFlashcardId(userId, flashcardId);
            if (reviewOpt.isPresent()) {
                currentBoxLevel = reviewOpt.get().getBoxLevel();
            }
        }

        boolean bookmarked = false;
        if (userId != null) {
            bookmarked = bookmarkRepository.existsByUserIdAndContentTypeAndContentId(userId, ContentType.FLASHCARD, flashcardId);
        }

        return new FlashcardDto(
                card.getId(),
                card.getTopic().getId(),
                card.getTopic().getName(),
                card.getTopic().getSubject().getId(),
                card.getTopic().getSubject().getName(),
                card.getQuestion(),
                card.getAnswer(),
                card.getExplanation(),
                card.getExampleCode(),
                card.getDifficulty(),
                card.getPriority(),
                currentBoxLevel,
                bookmarked,
                currentIndex,
                totalInTopic,
                prevId,
                nextId
        );
    }

    public FlashcardReview recordReview(Long userId, Long flashcardId, FlashcardRating rating) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Flashcard card = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found with id: " + flashcardId));

        FlashcardReview review = flashcardReviewRepository.findByUserIdAndFlashcardId(userId, flashcardId)
                .orElse(new FlashcardReview());

        if (review.getId() == null) {
            review.setUser(user);
            review.setFlashcard(card);
            review.setBoxLevel(1);
        }

        // Leitner System Logic (PRD Section 9.4):
        // FORGOT -> reset to Box 1 (earlier review)
        // ALMOST -> medium frequency (increase box level by 1 up to max 5, or keep level)
        // EASY   -> lower frequency (increase box level by 1 or 2 up to max 5)
        int currentBox = review.getBoxLevel();
        int newBox = 1;

        if (rating == FlashcardRating.FORGOT) {
            newBox = 1;
        } else if (rating == FlashcardRating.ALMOST) {
            newBox = Math.min(5, Math.max(2, currentBox));
        } else if (rating == FlashcardRating.EASY) {
            newBox = Math.min(5, currentBox + 1);
            if (currentBox == 1) newBox = 2; // Jump to Box 2 on Easy first try
        }

        review.setRating(rating);
        review.setBoxLevel(newBox);
        review.setReviewedAt(LocalDateTime.now());

        FlashcardReview savedReview = flashcardReviewRepository.save(review);

        // Log Activity
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivityType(ActivityType.FLASHCARD_REVIEWED);
        log.setContentType(ContentType.FLASHCARD);
        log.setContentId(flashcardId);
        log.setMetadata("Rated " + rating.name() + " (Leitner Box " + newBox + ")");
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);

        return savedReview;
    }

    @Transactional(readOnly = true)
    public List<FlashcardReview> getReviewHistory(Long userId, Long flashcardId) {
        return flashcardReviewRepository.findByUserIdAndFlashcardId(userId, flashcardId)
                .map(List::of)
                .orElse(List.of());
    }

    @Transactional(readOnly = true)
    public Long getFirstFlashcardIdForTopic(Long topicId) {
        List<Flashcard> cards = flashcardRepository.findByTopicIdAndActiveTrue(topicId);
        return cards.isEmpty() ? 1L : cards.get(0).getId();
    }
}
