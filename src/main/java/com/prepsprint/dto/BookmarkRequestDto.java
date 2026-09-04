package com.prepsprint.dto;

import com.prepsprint.enums.ContentType;
import jakarta.validation.constraints.NotNull;

public class BookmarkRequestDto {

    @NotNull(message = "Content type is required")
    private ContentType contentType;

    @NotNull(message = "Content ID is required")
    private Long contentId;

    public BookmarkRequestDto() {
    }

    public BookmarkRequestDto(ContentType contentType, Long contentId) {
        this.contentType = contentType;
        this.contentId = contentId;
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
}
