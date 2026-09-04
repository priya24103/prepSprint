package com.prepsprint.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "technical_questions", indexes = {
        @Index(name = "idx_tech_q_topic_id", columnList = "topic_id")
})
public class TechnicalQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = true)
    private Topic topic;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(name = "what_interviewer_checks", columnDefinition = "TEXT")
    private String whatInterviewerChecks;

    @Column(name = "short_answer", columnDefinition = "TEXT")
    private String shortAnswer;

    @Column(name = "detailed_answer", columnDefinition = "TEXT")
    private String detailedAnswer;

    @Column(name = "key_points", columnDefinition = "TEXT")
    private String keyPoints;

    @Column(columnDefinition = "TEXT")
    private String tips;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private boolean active = true;

    public TechnicalQuestion() {
    }

    public TechnicalQuestion(Long id, Topic topic, String question, String whatInterviewerChecks, String shortAnswer, String detailedAnswer, String keyPoints, String tips, int priority, boolean active) {
        this.id = id;
        this.topic = topic;
        this.question = question;
        this.whatInterviewerChecks = whatInterviewerChecks;
        this.shortAnswer = shortAnswer;
        this.detailedAnswer = detailedAnswer;
        this.keyPoints = keyPoints;
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

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getWhatInterviewerChecks() {
        return whatInterviewerChecks;
    }

    public void setWhatInterviewerChecks(String whatInterviewerChecks) {
        this.whatInterviewerChecks = whatInterviewerChecks;
    }

    public String getShortAnswer() {
        return shortAnswer;
    }

    public void setShortAnswer(String shortAnswer) {
        this.shortAnswer = shortAnswer;
    }

    public String getDetailedAnswer() {
        return detailedAnswer;
    }

    public void setDetailedAnswer(String detailedAnswer) {
        this.detailedAnswer = detailedAnswer;
    }

    public String getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(String keyPoints) {
        this.keyPoints = keyPoints;
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
