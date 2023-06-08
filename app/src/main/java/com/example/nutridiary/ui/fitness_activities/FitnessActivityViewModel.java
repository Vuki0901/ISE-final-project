package com.example.nutridiary.ui.fitness_activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FitnessActivityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FitnessActivityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}