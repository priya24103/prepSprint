package com.prepsprint.entity;

import com.prepsprint.enums.QuizMode;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts", indexes = {
        @Index(name = "idx_quiz_attempts_user_id", columnList = "user_id"),
        @Index(name = "idx_quiz_attempts_subject_id", columnList = "subject_id")
})
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

    public QuizAttempt() {
    }

    public QuizAttempt(Long id, User user, Subject subject, QuizMode mode, int score, int total, int durationSeconds, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.subject = subject;
        this.mode = mode;
        this.score = score;
        this.total = total;
        this.durationSeconds = durationSeconds;
        this.createdAt = createdAt;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public QuizMode getMode() {
        return mode;
    }

    public void setMode(QuizMode mode) {
        this.mode = mode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
