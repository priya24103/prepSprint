package com.prepsprint.dto;

import com.prepsprint.enums.FlashcardRating;
import jakarta.validation.constraints.NotNull;

public class FlashcardReviewRequestDto {

    @NotNull(message = "Rating is required")
    private FlashcardRating rating;

    public FlashcardReviewRequestDto() {
    }

    public FlashcardReviewRequestDto(FlashcardRating rating) {
        this.rating = rating;
    }

    public FlashcardRating getRating() {
        return rating;
    }

    public void setRating(FlashcardRating rating) {
        this.rating = rating;
    }
}
