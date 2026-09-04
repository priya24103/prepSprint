package com.prepsprint.dto;

import java.util.List;

public class QuizSubmitRequestDto {

    private List<QuestionAnswerDto> answers;
    private Integer durationSeconds;

    public QuizSubmitRequestDto() {
    }

    public QuizSubmitRequestDto(List<QuestionAnswerDto> answers, Integer durationSeconds) {
        this.answers = answers;
        this.durationSeconds = durationSeconds;
    }

    public List<QuestionAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionAnswerDto> answers) {
        this.answers = answers;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}
