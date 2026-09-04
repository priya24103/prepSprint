package com.prepsprint.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_attempts", indexes = {
        @Index(name = "idx_q_attempts_attempt_id", columnList = "attempt_id"),
        @Index(name = "idx_q_attempts_question_id", columnList = "question_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_option", length = 10)
    private String selectedOption;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @CreationTimestamp
    @Column(name = "answered_at", updatable = false)
    private LocalDateTime answeredAt;
}
