package com.prepsprint.entity;

import com.prepsprint.enums.ActivityType;
import com.prepsprint.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log", indexes = {
        @Index(name = "idx_act_log_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "content_id")
    private Long contentId;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
