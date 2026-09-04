package com.prepsprint.entity;

import com.prepsprint.enums.FlashcardRating;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "flashcard_reviews", indexes = {
        @Index(name = "idx_fc_reviews_user_id", columnList = "user_id"),
        @Index(name = "idx_fc_reviews_flashcard_id", columnList = "flashcard_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
