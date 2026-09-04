package com.prepsprint.controller;

import com.prepsprint.dto.FlashcardDto;
import com.prepsprint.entity.FlashcardReview;
import com.prepsprint.entity.User;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.FlashcardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final UserRepository userRepository;

    public FlashcardController(FlashcardService flashcardService, UserRepository userRepository) {
        this.flashcardService = flashcardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public String showFlashcardDetail(@PathVariable("id") Long id,
                                      Authentication authentication,
                                      Model model) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        FlashcardDto flashcard = flashcardService.getFlashcardDetail(userId, id);
        List<FlashcardReview> reviewHistory = flashcardService.getReviewHistory(userId, id);

        model.addAttribute("flashcard", flashcard);
        model.addAttribute("reviewHistory", reviewHistory);
        model.addAttribute("activeNav", "flashcards");

        return "flashcards/detail";
    }

    @GetMapping("/topic/{topicId}")
    public String showFlashcardsForTopic(@PathVariable("topicId") Long topicId) {
        Long firstId = flashcardService.getFirstFlashcardIdForTopic(topicId);
        return "redirect:/flashcards/" + firstId;
    }
}
