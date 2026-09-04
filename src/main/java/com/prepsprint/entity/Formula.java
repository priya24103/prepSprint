package com.prepsprint.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "formulas", indexes = {
        @Index(name = "idx_formulas_topic_id", columnList = "topic_id")
})
public class Formula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String formula;

    @Column(columnDefinition = "TEXT")
    private String variables;

    @Column(columnDefinition = "TEXT")
    private String shortcut;

    @Column(columnDefinition = "TEXT")
    private String example;

    @Column(nullable = false)
    private int priority;

    @Column(nullable = false)
    private boolean active = true;

    public Formula() {
    }

    public Formula(Long id, Topic topic, String title, String formula, String variables, String shortcut, String example, int priority, boolean active) {
        this.id = id;
        this.topic = topic;
        this.title = title;
        this.formula = formula;
        this.variables = variables;
        this.shortcut = shortcut;
        this.example = example;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
