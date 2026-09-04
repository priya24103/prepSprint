package com.prepsprint.dto;

import com.prepsprint.enums.ContentType;

import java.time.LocalDateTime;

public class BookmarkItemDto {
    private Long id;
    private ContentType contentType;
    private Long contentId;
    private String title;
    private String categoryName;
    private String targetUrl;
    private LocalDateTime createdAt;

    public BookmarkItemDto() {
    }

    public BookmarkItemDto(Long id, ContentType contentType, Long contentId, String title, String categoryName, String targetUrl, LocalDateTime createdAt) {
        this.id = id;
        this.contentType = contentType;
        this.contentId = contentId;
        this.title = title;
        this.categoryName = categoryName;
        this.targetUrl = targetUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
