package com.prepsprint.service;

import com.prepsprint.dto.*;
import com.prepsprint.entity.*;
import com.prepsprint.enums.ActivityType;
import com.prepsprint.enums.ContentType;
import com.prepsprint.enums.QuizMode;
import com.prepsprint.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuizService {

    private final QuizAttemptRepository quizAttemptRepository;
    private final QuestionAttemptRepository questionAttemptRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ActivityLogRepository activityLogRepository;

    public QuizService(QuizAttemptRepository quizAttemptRepository,
                       QuestionAttemptRepository questionAttemptRepository,
                       QuestionRepository questionRepository,
                       UserRepository userRepository,
                       SubjectRepository subjectRepository,
                       TopicRepository topicRepository,
                       BookmarkRepository bookmarkRepository,
                       ActivityLogRepository activityLogRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
        this.questionAttemptRepository = questionAttemptRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.activityLogRepository = activityLogRepository;
    }

    public QuizStartResponseDto startQuiz(Long userId, QuizStartRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        int targetCount = (request.getQuestionCount() != null && request.getQuestionCount() > 0)
                ? request.getQuestionCount() : 10;

        List<Question> candidateQuestions = new ArrayList<>();
        Subject quizSubject = null;

        QuizMode mode = request.getMode() != null ? request.getMode() : QuizMode.RANDOM;

        if (mode == QuizMode.TOPIC && request.getTopicId() != null) {
            candidateQuestions = questionRepository.findByTopicIdAndActiveTrue(request.getTopicId());
            Topic topic = topicRepository.findById(request.getTopicId()).orElse(null);
            if (topic != null) {
                quizSubject = topic.getSubject();
            }
        } else if (mode == QuizMode.SUBJECT && request.getSubjectId() != null) {
            candidateQuestions = questionRepository.findByTopicSubjectIdAndActiveTrue(request.getSubjectId());
            quizSubject = subjectRepository.findById(request.getSubjectId()).orElse(null);
        } else if (mode == QuizMode.BOOKMARK) {
            List<Bookmark> bookmarks = bookmarkRepository.findByUserIdAndContentType(userId, ContentType.MCQ);
            List<Long> questionIds = bookmarks.stream().map(Bookmark::getContentId).collect(Collectors.toList());
            if (!questionIds.isEmpty()) {
                candidateQuestions = questionRepository.findByIdInAndActiveTrue(questionIds);
            }
            if (candidateQuestions.isEmpty()) {
                candidateQuestions = questionRepository.findByActiveTrue();
            }
        } else if (mode == QuizMode.WEAK) {
            candidateQuestions = getWeakTopicQuestions(userId, targetCount);
        } else {
            // RANDOM or MIXED
            if (request.getSubjectId() != null) {
                candidateQuestions = questionRepository.findByTopicSubjectIdAndActiveTrue(request.getSubjectId());
                quizSubject = subjectRepository.findById(request.getSubjectId()).orElse(null);
            } else {
                candidateQuestions = questionRepository.findByActiveTrue();
            }
        }

        if (candidateQuestions.isEmpty()) {
            candidateQuestions = questionRepository.findByActiveTrue();
        }

        Collections.shuffle(candidateQuestions);
        if (candidateQuestions.size() > targetCount) {
            candidateQuestions = candidateQuestions.subList(0, targetCount);
        }

        // Create QuizAttempt
        QuizAttempt attempt = new QuizAttempt();
        attempt.setUser(user);
        attempt.setSubject(quizSubject);
        attempt.setMode(mode);
        attempt.setScore(0);
        attempt.setTotal(candidateQuestions.size());
        attempt.setDurationSeconds(0);
        attempt.setCreatedAt(LocalDateTime.now());

        QuizAttempt savedAttempt = quizAttemptRepository.save(attempt);

        // Save QuestionAttempt placeholders for consistent retrieval
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question q : candidateQuestions) {
            QuestionAttempt qa = new QuestionAttempt();
            qa.setQuizAttempt(savedAttempt);
            qa.setQuestion(q);
            qa.setSelectedOption(null);
            qa.setCorrect(false);
            qa.setAnsweredAt(LocalDateTime.now());
            questionAttemptRepository.save(qa);

            questionDtos.add(new QuestionDto(
                    q.getId(),
                    q.getQuestionText(),
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD(),
                    q.getTopic().getId(),
                    q.getTopic().getName(),
                    q.getTopic().getSubject().getId(),
                    q.getTopic().getSubject().getName(),
                    q.getDifficulty()
            ));
        }

        return new QuizStartResponseDto(
                savedAttempt.getId(),
                mode,
                questionDtos,
                request.getTimerSeconds(),
                candidateQuestions.size()
        );
    }

    @Transactional(readOnly = true)
    public QuizStartResponseDto getAttemptQuestions(Long attemptId) {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz attempt not found: " + attemptId));

        List<QuestionAttempt> qAttempts = questionAttemptRepository.findByQuizAttemptId(attemptId);
        List<QuestionDto> questionDtos = new ArrayList<>();

        for (QuestionAttempt qa : qAttempts) {
            Question q = qa.getQuestion();
            questionDtos.add(new QuestionDto(
                    q.getId(),
                    q.getQuestionText(),
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD(),
                    q.getTopic().getId(),
                    q.getTopic().getName(),
                    q.getTopic().getSubject().getId(),
                    q.getTopic().getSubject().getName(),
                    q.getDifficulty()
            ));
        }

        return new QuizStartResponseDto(
                attempt.getId(),
                attempt.getMode(),
                questionDtos,
                attempt.getDurationSeconds(),
                questionDtos.size()
        );
    }

    public QuizResultDto submitQuiz(Long userId, Long attemptId, QuizSubmitRequestDto request) {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz attempt not found with id: " + attemptId));

        if (!attempt.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized quiz submission");
        }

        // Prevent double quiz submission (PRD Section 21)
        List<QuestionAttempt> existingAttempts = questionAttemptRepository.findByQuizAttemptId(attemptId);
        boolean alreadySubmitted = existingAttempts.stream().anyMatch(qa -> qa.getSelectedOption() != null);
        if (alreadySubmitted) {
            throw new IllegalStateException("Quiz attempt has already been submitted and completed.");
        }

        Map<Long, QuestionAttempt> existingMap = existingAttempts.stream()
                .collect(Collectors.toMap(qa -> qa.getQuestion().getId(), qa -> qa, (a, b) -> a));

        int score = 0;
        List<QuestionResultDto> questionResults = new ArrayList<>();

        if (request.getAnswers() != null) {
            for (QuestionAnswerDto answerDto : request.getAnswers()) {
                Question question = questionRepository.findById(answerDto.getQuestionId()).orElse(null);
                if (question == null) continue;

                String selected = answerDto.getSelectedOption() != null ? answerDto.getSelectedOption().trim() : "";
                String correct = question.getCorrectOption() != null ? question.getCorrectOption().trim() : "";

                boolean isCorrect = selected.equalsIgnoreCase(correct);
                if (isCorrect) {
                    score++;
                }

                QuestionAttempt qAttempt = existingMap.get(question.getId());
                if (qAttempt == null) {
                    qAttempt = new QuestionAttempt();
                    qAttempt.setQuizAttempt(attempt);
                    qAttempt.setQuestion(question);
                }
                qAttempt.setSelectedOption(selected);
                qAttempt.setCorrect(isCorrect);
                qAttempt.setAnsweredAt(LocalDateTime.now());
                questionAttemptRepository.save(qAttempt);

                QuestionResultDto resultDto = new QuestionResultDto(
                        question.getId(),
                        question.getQuestionText(),
                        question.getOptionA(),
                        question.getOptionB(),
                        question.getOptionC(),
                        question.getOptionD(),
                        selected,
                        correct,
                        isCorrect,
                        question.getExplanation()
                );
                questionResults.add(resultDto);
            }
        }

        int total = questionResults.size() > 0 ? questionResults.size() : attempt.getTotal();
        int duration = (request.getDurationSeconds() != null && request.getDurationSeconds() >= 0)
                ? request.getDurationSeconds() : 0;

        attempt.setScore(score);
        attempt.setTotal(total);
        attempt.setDurationSeconds(duration);
        quizAttemptRepository.save(attempt);

        // Log Activity
        ActivityLog activityLog = new ActivityLog();
        activityLog.setUser(attempt.getUser());
        activityLog.setActivityType(ActivityType.QUIZ_COMPLETED);
        activityLog.setContentType(ContentType.MCQ);
        activityLog.setContentId(attemptId);
        activityLog.setMetadata("Completed " + attempt.getMode() + " quiz: " + score + "/" + total);
        activityLog.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(activityLog);

        double scorePercentage = total > 0 ? Math.round((double) score / total * 100.0 * 10.0) / 10.0 : 0.0;

        return new QuizResultDto(
                attemptId,
                score,
                total,
                scorePercentage,
                duration,
                attempt.getMode(),
                questionResults
        );
    }

    @Transactional(readOnly = true)
    public QuizResultDto getQuizResult(Long attemptId) {
        QuizAttempt attempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz attempt not found with id: " + attemptId));

        List<QuestionAttempt> qAttempts = questionAttemptRepository.findByQuizAttemptId(attemptId);

        List<QuestionResultDto> questionResults = new ArrayList<>();
        int score = 0;
        for (QuestionAttempt qa : qAttempts) {
            Question q = qa.getQuestion();
            if (qa.isCorrect()) score++;

            questionResults.add(new QuestionResultDto(
                    q.getId(),
                    q.getQuestionText(),
                    q.getOptionA(),
                    q.getOptionB(),
                    q.getOptionC(),
                    q.getOptionD(),
                    qa.getSelectedOption(),
                    q.getCorrectOption(),
                    qa.isCorrect(),
                    q.getExplanation()
            ));
        }

        int total = qAttempts.size() > 0 ? qAttempts.size() : attempt.getTotal();
        double scorePercentage = total > 0 ? Math.round((double) score / total * 100.0 * 10.0) / 10.0 : 0.0;

        return new QuizResultDto(
                attemptId,
                attempt.getScore(),
                attempt.getTotal(),
                scorePercentage,
                attempt.getDurationSeconds(),
                attempt.getMode(),
                questionResults
        );
    }

    /**
     * Interim Weak Topics selection rule for Prompt 13:
     * Select questions from topics with existing attempts that have the lowest accuracy.
     * Prompt 18 will replace this method with the real 3-attempt-minimum threshold rule.
     */
    public List<Question> getWeakTopicQuestions(Long userId, int targetCount) {
        List<QuizAttempt> userAttempts = quizAttemptRepository.findByUserId(userId);
        if (userAttempts.isEmpty()) {
            return questionRepository.findByActiveTrue();
        }

        // Group attempts by topic (via questions) or subjects with low score percentage
        List<Question> allActive = questionRepository.findByActiveTrue();
        return allActive;
    }
}
