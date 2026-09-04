package com.prepsprint.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hr_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
