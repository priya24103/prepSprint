package com.prepsprint.dto;

import com.prepsprint.enums.QuizMode;

import java.util.List;

public class QuizResultDto {

    private Long attemptId;
    private int score;
    private int total;
    private double scorePercentage;
    private int durationSeconds;
    private QuizMode mode;
    private List<QuestionResultDto> questionResults;

    public QuizResultDto() {
    }

    public QuizResultDto(Long attemptId, int score, int total, double scorePercentage, int durationSeconds, QuizMode mode, List<QuestionResultDto> questionResults) {
        this.attemptId = attemptId;
        this.score = score;
        this.total = total;
        this.scorePercentage = scorePercentage;
        this.durationSeconds = durationSeconds;
        this.mode = mode;
        this.questionResults = questionResults;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(double scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds != null ? durationSeconds : 0;
    }

    public QuizMode getMode() {
        return mode;
    }

    public void setMode(QuizMode mode) {
        this.mode = mode;
    }

    public List<QuestionResultDto> getQuestionResults() {
        return questionResults;
    }

    public void setQuestionResults(List<QuestionResultDto> questionResults) {
        this.questionResults = questionResults;
    }
}
