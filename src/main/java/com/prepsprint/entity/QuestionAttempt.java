package com.prepsprint.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "question_attempts", indexes = {
        @Index(name = "idx_q_attempts_attempt_id", columnList = "attempt_id"),
        @Index(name = "idx_q_attempts_question_id", columnList = "question_id")
})
public class QuestionAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_option", length = 10)
    private String selectedOption;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @CreationTimestamp
    @Column(name = "answered_at", updatable = false)
    private LocalDateTime answeredAt;

    public QuestionAttempt() {
    }

    public QuestionAttempt(Long id, QuizAttempt quizAttempt, Question question, String selectedOption, boolean isCorrect, LocalDateTime answeredAt) {
        this.id = id;
        this.quizAttempt = quizAttempt;
        this.question = question;
        this.selectedOption = selectedOption;
        this.isCorrect = isCorrect;
        this.answeredAt = answeredAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuizAttempt getQuizAttempt() {
        return quizAttempt;
    }

    public void setQuizAttempt(QuizAttempt quizAttempt) {
        this.quizAttempt = quizAttempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }
}
