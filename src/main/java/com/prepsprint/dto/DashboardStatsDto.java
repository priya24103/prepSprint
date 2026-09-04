package com.prepsprint.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardStatsDto {

    private int preparationScore;
    private int questionsSolved;
    private int questionsCorrect;
    private int flashcardsReviewed;
    private int formulasRevised;
    private double quizAccuracy;
    private int currentStreak;
    private int bookmarkCount;
    private int weakTopicCount;
    private List<ActivityDto> recentActivities = new ArrayList<>();

    public DashboardStatsDto() {
    }

    public DashboardStatsDto(int preparationScore, int questionsSolved, int questionsCorrect, int flashcardsReviewed, int formulasRevised, double quizAccuracy, int currentStreak, int bookmarkCount, int weakTopicCount, List<ActivityDto> recentActivities) {
        this.preparationScore = preparationScore;
        this.questionsSolved = questionsSolved;
        this.questionsCorrect = questionsCorrect;
        this.flashcardsReviewed = flashcardsReviewed;
        this.formulasRevised = formulasRevised;
        this.quizAccuracy = quizAccuracy;
        this.currentStreak = currentStreak;
        this.bookmarkCount = bookmarkCount;
        this.weakTopicCount = weakTopicCount;
        this.recentActivities = recentActivities != null ? recentActivities : new ArrayList<>();
    }

    public int getPreparationScore() {
        return preparationScore;
    }

    public void setPreparationScore(int preparationScore) {
        this.preparationScore = preparationScore;
    }

    public int getQuestionsSolved() {
        return questionsSolved;
    }

    public void setQuestionsSolved(int questionsSolved) {
        this.questionsSolved = questionsSolved;
    }

    public int getQuestionsCorrect() {
        return questionsCorrect;
    }

    public void setQuestionsCorrect(int questionsCorrect) {
        this.questionsCorrect = questionsCorrect;
    }

    public int getFlashcardsReviewed() {
        return flashcardsReviewed;
    }

    public void setFlashcardsReviewed(int flashcardsReviewed) {
        this.flashcardsReviewed = flashcardsReviewed;
    }

    public int getFormulasRevised() {
        return formulasRevised;
    }

    public void setFormulasRevised(int formulasRevised) {
        this.formulasRevised = formulasRevised;
    }

    public double getQuizAccuracy() {
        return quizAccuracy;
    }

    public void setQuizAccuracy(double quizAccuracy) {
        this.quizAccuracy = quizAccuracy;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(int bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public int getWeakTopicCount() {
        return weakTopicCount;
    }

    public void setWeakTopicCount(int weakTopicCount) {
        this.weakTopicCount = weakTopicCount;
    }

    public List<ActivityDto> getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(List<ActivityDto> recentActivities) {
        this.recentActivities = recentActivities;
    }
}
