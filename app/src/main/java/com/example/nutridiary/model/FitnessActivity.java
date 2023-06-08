package com.example.nutridiary.model;

public class FitnessActivity {
    private int id;
    private String title;
    private String activityType;
    private int duration;

    // Constructor
    public FitnessActivity(int id, String title, String activityType, int duration) {
        this.id = id;
        this.title = title;
        this.activityType = activityType;
        this.duration = duration;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getActivityType() {
        return activityType;
    }

    public int getDuration() {
        return duration;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
