package com.prepsprint.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formulas", indexes = {
        @Index(name = "idx_formulas_topic_id", columnList = "topic_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
