package com.prepsprint.entity;

import com.prepsprint.enums.ContentType;
import jakarta.persistence.*;

@Entity
@Table(name = "session_items", indexes = {
        @Index(name = "idx_session_items_session_id", columnList = "session_id")
})
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

    public SessionItem() {
    }

    public SessionItem(Long id, RevisionSession revisionSession, ContentType contentType, Long contentId, int position, boolean completed, String result) {
        this.id = id;
        this.revisionSession = revisionSession;
        this.contentType = contentType;
        this.contentId = contentId;
        this.position = position;
        this.completed = completed;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RevisionSession getRevisionSession() {
        return revisionSession;
    }

    public void setRevisionSession(RevisionSession revisionSession) {
        this.revisionSession = revisionSession;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
