package com.prepsprint.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hr_questions")
public class HrQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(name = "interviewer_intent", columnDefinition = "TEXT")
    private String interviewerIntent;

    @Column(name = "answer_structure", columnDefinition = "TEXT")
    private String answerStructure;

    @Column(name = "sample_guidance", columnDefinition = "TEXT")
    private String sampleGuidance;

    @Column(columnDefinition = "TEXT")
    private String tips;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private boolean active = true;

    public HrQuestion() {
    }

    public HrQuestion(Long id, String question, String interviewerIntent, String answerStructure, String sampleGuidance, String tips, int priority, boolean active) {
        this.id = id;
        this.question = question;
        this.interviewerIntent = interviewerIntent;
        this.answerStructure = answerStructure;
        this.sampleGuidance = sampleGuidance;
        this.tips = tips;
        this.priority = priority;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getInterviewerIntent() {
        return interviewerIntent;
    }

    public void setInterviewerIntent(String interviewerIntent) {
        this.interviewerIntent = interviewerIntent;
    }

    public String getAnswerStructure() {
        return answerStructure;
    }

    public void setAnswerStructure(String answerStructure) {
        this.answerStructure = answerStructure;
    }

    public String getSampleGuidance() {
        return sampleGuidance;
    }

    public void setSampleGuidance(String sampleGuidance) {
        this.sampleGuidance = sampleGuidance;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
