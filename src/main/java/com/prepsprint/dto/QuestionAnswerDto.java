package com.prepsprint.dto;

public class QuestionAnswerDto {

    private Long questionId;
    private String selectedOption;

    public QuestionAnswerDto() {
    }

    public QuestionAnswerDto(Long questionId, String selectedOption) {
        this.questionId = questionId;
        this.selectedOption = selectedOption;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
