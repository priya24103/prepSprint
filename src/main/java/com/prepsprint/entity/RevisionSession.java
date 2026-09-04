package com.prepsprint.entity;

import com.prepsprint.enums.RevisionMode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "revision_sessions", indexes = {
        @Index(name = "idx_rev_sessions_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
