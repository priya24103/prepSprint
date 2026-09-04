package com.prepsprint.dto;

public class TopicSummaryDto {
    private Long id;
    private Long subjectId;
    private String subjectName;
    private String name;
    private String description;
    private int flashcardCount;
    private int formulaCount;
    private int mcqCount;
    private double progressPercentage;
    private double accuracyPercentage;

    public TopicSummaryDto() {
    }

    public TopicSummaryDto(Long id, Long subjectId, String subjectName, String name, String description, int flashcardCount, int formulaCount, int mcqCount, double progressPercentage, double accuracyPercentage) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.name = name;
        this.description = description;
        this.flashcardCount = flashcardCount;
        this.formulaCount = formulaCount;
        this.mcqCount = mcqCount;
        this.progressPercentage = progressPercentage;
        this.accuracyPercentage = accuracyPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFlashcardCount() {
        return flashcardCount;
    }

    public void setFlashcardCount(int flashcardCount) {
        this.flashcardCount = flashcardCount;
    }

    public int getFormulaCount() {
        return formulaCount;
    }

    public void setFormulaCount(int formulaCount) {
        this.formulaCount = formulaCount;
    }

    public int getMcqCount() {
        return mcqCount;
    }

    public void setMcqCount(int mcqCount) {
        this.mcqCount = mcqCount;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public double getAccuracyPercentage() {
        return accuracyPercentage;
    }

    public void setAccuracyPercentage(double accuracyPercentage) {
        this.accuracyPercentage = accuracyPercentage;
    }
}
