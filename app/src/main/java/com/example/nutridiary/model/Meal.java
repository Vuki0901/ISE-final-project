package com.example.nutridiary.model;

import java.util.Date;

public class Meal {
    private int id;
    private String dishName;
    private int calorieCount;
    private Date date;
    private String mealCategory;

    // Constructor
    public Meal(int id, String dishName, int calorieCount, Date date, String mealCategory) {
        this.id = id;
        this.dishName = dishName;
        this.calorieCount = calorieCount;
        this.date = date;
        this.mealCategory = mealCategory;
    }

    public Meal(String dishName, int calorieCount, Date date, String mealCategory) {
        this.dishName = dishName;
        this.calorieCount = calorieCount;
        this.date = date;
        this.mealCategory = mealCategory;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDishName() {
        return dishName;
    }

    public int getCalorieCount() {
        return calorieCount;
    }

    public Date getDate() {
        return date;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setCalorieCount(int calorieCount) {
        this.calorieCount = calorieCount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }
}
