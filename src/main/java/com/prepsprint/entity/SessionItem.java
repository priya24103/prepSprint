package com.prepsprint.entity;

import com.prepsprint.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "session_items", indexes = {
        @Index(name = "idx_session_items_session_id", columnList = "session_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private RevisionSession revisionSession;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private int position;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(columnDefinition = "TEXT")
    private String result;
}
