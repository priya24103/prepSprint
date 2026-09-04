package com.prepsprint.entity;

import com.prepsprint.enums.Difficulty;
import jakarta.persistence.*;

@Entity
@Table(name = "flashcards", indexes = {
        @Index(name = "idx_flashcards_topic_id", columnList = "topic_id")
})
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "example_code", columnDefinition = "TEXT")
    private String exampleCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private boolean active = true;

    public Flashcard() {
    }

    public Flashcard(Long id, Topic topic, String question, String answer, String explanation, String exampleCode, Difficulty difficulty, int priority, boolean active) {
        this.id = id;
        this.topic = topic;
        this.question = question;
        this.answer = answer;
        this.explanation = explanation;
        this.exampleCode = exampleCode;
        this.difficulty = difficulty;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
