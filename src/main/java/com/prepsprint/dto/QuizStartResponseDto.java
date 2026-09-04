package com.prepsprint.dto;

import com.prepsprint.enums.QuizMode;

import java.util.List;

public class QuizStartResponseDto {

    private Long attemptId;
    private QuizMode mode;
    private List<QuestionDto> questions;
    private Integer timerSeconds;
    private int totalQuestions;

    public QuizStartResponseDto() {
    }

    public QuizStartResponseDto(Long attemptId, QuizMode mode, List<QuestionDto> questions, Integer timerSeconds, int totalQuestions) {
        this.attemptId = attemptId;
        this.mode = mode;
        this.questions = questions;
        this.timerSeconds = timerSeconds;
        this.totalQuestions = totalQuestions;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public QuizMode getMode() {
        return mode;
    }

    public void setMode(QuizMode mode) {
        this.mode = mode;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public Integer getTimerSeconds() {
        return timerSeconds;
    }

    public void setTimerSeconds(Integer timerSeconds) {
        this.timerSeconds = timerSeconds;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
