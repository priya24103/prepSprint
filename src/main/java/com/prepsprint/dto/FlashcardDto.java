package com.prepsprint.dto;

import com.prepsprint.enums.Difficulty;

public class FlashcardDto {
    private Long id;
    private Long topicId;
    private String topicName;
    private Long subjectId;
    private String subjectName;
    private String question;
    private String answer;
    private String explanation;
    private String exampleCode;
    private Difficulty difficulty;
    private int priority;
    private int currentBoxLevel;
    private boolean bookmarked;
    private int currentIndex;
    private int totalInTopic;
    private Long prevFlashcardId;
    private Long nextFlashcardId;

    public FlashcardDto() {
    }

    public FlashcardDto(Long id, Long topicId, String topicName, Long subjectId, String subjectName, String question, String answer, String explanation, String exampleCode, Difficulty difficulty, int priority, int currentBoxLevel, boolean bookmarked, int currentIndex, int totalInTopic, Long prevFlashcardId, Long nextFlashcardId) {
        this.id = id;
        this.topicId = topicId;
        this.topicName = topicName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.exampleCode = exampleCode;
        this.difficulty = difficulty;
        this.priority = priority;
        this.currentBoxLevel = currentBoxLevel;
        this.bookmarked = bookmarked;
        this.currentIndex = currentIndex;
        this.totalInTopic = totalInTopic;
        this.prevFlashcardId = prevFlashcardId;
        this.nextFlashcardId = nextFlashcardId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExampleCode() {
        return exampleCode;
    }

    public void setExampleCode(String exampleCode) {
        this.exampleCode = exampleCode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCurrentBoxLevel() {
        return currentBoxLevel;
    }

    public void setCurrentBoxLevel(int currentBoxLevel) {
        this.currentBoxLevel = currentBoxLevel;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getTotalInTopic() {
        return totalInTopic;
    }

    public void setTotalInTopic(int totalInTopic) {
        this.totalInTopic = totalInTopic;
    }

    public Long getPrevFlashcardId() {
        return prevFlashcardId;
    }

    public void setPrevFlashcardId(Long prevFlashcardId) {
        this.prevFlashcardId = prevFlashcardId;
    }

    public Long getNextFlashcardId() {
        return nextFlashcardId;
    }

    public void setNextFlashcardId(Long nextFlashcardId) {
        this.nextFlashcardId = nextFlashcardId;
    }
}
