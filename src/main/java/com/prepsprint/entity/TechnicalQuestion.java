package com.prepsprint.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "technical_questions", indexes = {
        @Index(name = "idx_tech_q_topic_id", columnList = "topic_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
