package com.prepsprint.dto;

import com.prepsprint.enums.QuizMode;
import jakarta.validation.constraints.NotNull;

public class QuizStartRequestDto {

    @NotNull(message = "Quiz mode is required")
    private QuizMode mode;

    private Long subjectId;
    private Long topicId;
    private Integer questionCount;
    private Integer timerSeconds;

    public QuizStartRequestDto() {
    }

    public QuizStartRequestDto(QuizMode mode, Long subjectId, Long topicId, Integer questionCount, Integer timerSeconds) {
        this.mode = mode;
        this.subjectId = subjectId;
        this.topicId = topicId;
        this.questionCount = questionCount;
        this.timerSeconds = timerSeconds;
    }

    public QuizMode getMode() {
        return mode;
    }

    public void setMode(QuizMode mode) {
        this.mode = mode;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getTimerSeconds() {
        return timerSeconds;
    }

    public void setTimerSeconds(Integer timerSeconds) {
        this.timerSeconds = timerSeconds;
    }
}
