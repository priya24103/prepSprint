package com.prepsprint.entity;

import com.prepsprint.enums.FlashcardRating;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "flashcard_reviews", indexes = {
        @Index(name = "idx_fc_reviews_user_id", columnList = "user_id"),
        @Index(name = "idx_fc_reviews_flashcard_id", columnList = "flashcard_id")
})
public class FlashcardReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlashcardRating rating;

    @Column(name = "box_level", nullable = false)
    private int boxLevel;

    @CreationTimestamp
    @Column(name = "reviewed_at", updatable = false)
    private LocalDateTime reviewedAt;

    public FlashcardReview() {
    }

    public FlashcardReview(Long id, User user, Flashcard flashcard, FlashcardRating rating, int boxLevel, LocalDateTime reviewedAt) {
        this.id = id;
        this.user = user;
        this.flashcard = flashcard;
        this.rating = rating;
        this.boxLevel = boxLevel;
        this.reviewedAt = reviewedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public FlashcardRating getRating() {
        return rating;
    }

    public void setRating(FlashcardRating rating) {
        this.rating = rating;
    }

    public int getBoxLevel() {
        return boxLevel;
    }

    public void setBoxLevel(int boxLevel) {
        this.boxLevel = boxLevel;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}
