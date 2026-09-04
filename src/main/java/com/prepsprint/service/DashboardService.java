package com.prepsprint.service;

import com.prepsprint.dto.DashboardStatsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DashboardService {

    public DashboardStatsDto getStats(Long userId) {
        // Scaffolding implementation returning initial zero stats.
        // Will be wired to real Quiz, Progress, and Recommendation services in Prompt 17.
        return new DashboardStatsDto(
                0,    // preparationScore
                0,    // questionsSolved
                0,    // questionsCorrect
                0,    // flashcardsReviewed
                0,    // formulasRevised
                0.0,  // quizAccuracy
                0,    // currentStreak
                0,    // bookmarkCount
                0,    // weakTopicCount
                new ArrayList<>() // recentActivities
        );
    }
}
