package com.prepsprint.entity;

import com.prepsprint.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmarks", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_content", columnNames = {"user_id", "content_type", "content_id"})
}, indexes = {
        @Index(name = "idx_bookmarks_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
