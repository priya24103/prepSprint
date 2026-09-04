package com.prepsprint.entity;

import com.prepsprint.enums.RevisionMode;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "revision_sessions", indexes = {
        @Index(name = "idx_rev_sessions_user_id", columnList = "user_id")
})
public class RevisionSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RevisionMode mode;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @CreationTimestamp
    @Column(name = "started_at", updatable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(nullable = false)
    private boolean completed = false;

    public RevisionSession() {
    }

    public RevisionSession(Long id, User user, RevisionMode mode, int durationMinutes, LocalDateTime startedAt, LocalDateTime completedAt, boolean completed) {
        this.id = id;
        this.user = user;
        this.mode = mode;
        this.durationMinutes = durationMinutes;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.completed = completed;
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

    public RevisionMode getMode() {
        return mode;
    }

    public void setMode(RevisionMode mode) {
        this.mode = mode;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
