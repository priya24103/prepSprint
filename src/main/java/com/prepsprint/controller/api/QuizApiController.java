package com.prepsprint.controller.api;

import com.prepsprint.dto.QuizResultDto;
import com.prepsprint.dto.QuizStartRequestDto;
import com.prepsprint.dto.QuizStartResponseDto;
import com.prepsprint.dto.QuizSubmitRequestDto;
import com.prepsprint.entity.User;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizzes")
public class QuizApiController {

    private final QuizService quizService;
    private final UserRepository userRepository;

    public QuizApiController(QuizService quizService, UserRepository userRepository) {
        this.quizService = quizService;
        this.userRepository = userRepository;
    }

    @PostMapping("/start")
    public ResponseEntity<QuizStartResponseDto> startQuiz(@Valid @RequestBody QuizStartRequestDto request,
                                                           Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        QuizStartResponseDto response = quizService.startQuiz(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<QuizStartResponseDto> getQuizQuestions(@PathVariable("id") Long id) {
        QuizStartResponseDto response = quizService.getAttemptQuestions(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<QuizResultDto> submitQuiz(@PathVariable("id") Long id,
                                                     @Valid @RequestBody QuizSubmitRequestDto request,
                                                     Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        QuizResultDto result = quizService.submitQuiz(userId, id, request);
        return ResponseEntity.ok(result);
    }
}
