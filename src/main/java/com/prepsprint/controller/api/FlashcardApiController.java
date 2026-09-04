package com.prepsprint.controller.api;

import com.prepsprint.dto.FlashcardReviewRequestDto;
import com.prepsprint.entity.FlashcardReview;
import com.prepsprint.entity.User;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardApiController {

    private final FlashcardService flashcardService;
    private final UserRepository userRepository;

    public FlashcardApiController(FlashcardService flashcardService, UserRepository userRepository) {
        this.flashcardService = flashcardService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Map<String, Object>> recordReview(@PathVariable("id") Long id,
                                                            @Valid @RequestBody FlashcardReviewRequestDto request,
                                                            Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        FlashcardReview review = flashcardService.recordReview(userId, id, request.getRating());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "flashcardId", id,
                "rating", review.getRating().name(),
                "boxLevel", review.getBoxLevel()
        ));
    }
}
