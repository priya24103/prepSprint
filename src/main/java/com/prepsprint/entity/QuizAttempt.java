package com.prepsprint.entity;

import com.prepsprint.enums.QuizMode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts", indexes = {
        @Index(name = "idx_quiz_attempts_user_id", columnList = "user_id"),
        @Index(name = "idx_quiz_attempts_subject_id", columnList = "subject_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = true)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuizMode mode;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int total;

    @Column(name = "duration_seconds", nullable = false)
    private int durationSeconds;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
