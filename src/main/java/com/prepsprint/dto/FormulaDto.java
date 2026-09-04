package com.prepsprint.dto;

public class FormulaDto {
    private Long id;
    private Long topicId;
    private String topicName;
    private Long subjectId;
    private String subjectName;
    private String title;
    private String formula;
    private String variables;
    private String shortcut;
    private String example;
    private int priority;
    private boolean bookmarked;

    public FormulaDto() {
    }

    public FormulaDto(Long id, Long topicId, String topicName, Long subjectId, String subjectName, String title, String formula, String variables, String shortcut, String example, int priority, boolean bookmarked) {
        this.id = id;
        this.topicId = topicId;
        this.topicName = topicName;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.title = title;
        this.formula = formula;
        this.variables = variables;
        this.shortcut = shortcut;
        this.example = example;
        this.priority = priority;
        this.bookmarked = bookmarked;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }
}
