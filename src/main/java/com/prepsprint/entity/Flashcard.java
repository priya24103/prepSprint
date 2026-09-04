package com.prepsprint.entity;

import com.prepsprint.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flashcards", indexes = {
        @Index(name = "idx_flashcards_topic_id", columnList = "topic_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
