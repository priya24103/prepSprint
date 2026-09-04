package com.prepsprint.controller;

import com.prepsprint.dto.QuizResultDto;
import com.prepsprint.dto.QuizStartRequestDto;
import com.prepsprint.dto.QuizStartResponseDto;
import com.prepsprint.entity.Subject;
import com.prepsprint.entity.Topic;
import com.prepsprint.entity.User;
import com.prepsprint.enums.QuizMode;
import com.prepsprint.repository.SubjectRepository;
import com.prepsprint.repository.TopicRepository;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.QuizService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public QuizController(QuizService quizService,
                          SubjectRepository subjectRepository,
                          TopicRepository topicRepository,
                          UserRepository userRepository) {
        this.quizService = quizService;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"", "/", "/{dummyId}"})
    public String showQuizIndex(@PathVariable(value = "dummyId", required = false) String dummyId,
                                @RequestParam(value = "subjectId", required = false) Long subjectId,
                                @RequestParam(value = "topicId", required = false) Long topicId,
                                Model model) {
        List<Subject> subjects = subjectRepository.findByActiveTrue();
        List<Topic> topics = subjectId != null
                ? topicRepository.findBySubjectIdAndActiveTrue(subjectId)
                : topicRepository.findByActiveTrue();

        model.addAttribute("subjects", subjects);
        model.addAttribute("topics", topics);
        model.addAttribute("selectedSubjectId", subjectId);
        model.addAttribute("selectedTopicId", topicId);
        model.addAttribute("quizModes", QuizMode.values());
        model.addAttribute("activeNav", "quiz");

        return "quiz/index";
    }

    @GetMapping("/topic/{topicId}")
    public String startTopicQuiz(@PathVariable("topicId") Long topicId,
                                 Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        QuizStartRequestDto dto = new QuizStartRequestDto(QuizMode.TOPIC, null, topicId, 10, null);
        QuizStartResponseDto response = quizService.startQuiz(userId, dto);

        return "redirect:/quiz/attempt/" + response.getAttemptId();
    }

    @GetMapping("/subject/{subjectId}")
    public String startSubjectQuiz(@PathVariable("subjectId") Long subjectId,
                                   Authentication authentication) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);
        Long userId = user != null ? user.getId() : 1L;

        QuizStartRequestDto dto = new QuizStartRequestDto(QuizMode.SUBJECT, subjectId, null, 15, null);
        QuizStartResponseDto response = quizService.startQuiz(userId, dto);

        return "redirect:/quiz/attempt/" + response.getAttemptId();
    }

    @GetMapping("/attempt/{attemptId}")
    public String takeQuizAttempt(@PathVariable("attemptId") Long attemptId, Model model) {
        model.addAttribute("attemptId", attemptId);
        model.addAttribute("activeNav", "quiz");

        return "quiz/take";
    }

    @GetMapping("/result/{attemptId}")
    public String showQuizResult(@PathVariable("attemptId") Long attemptId, Model model) {
        QuizResultDto result = quizService.getQuizResult(attemptId);

        model.addAttribute("result", result);
        model.addAttribute("activeNav", "quiz");

        return "quiz/result";
    }
}
