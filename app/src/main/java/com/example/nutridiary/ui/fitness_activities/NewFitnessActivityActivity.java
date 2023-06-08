package com.example.nutridiary.ui.fitness_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nutridiary.R;
import com.example.nutridiary.Repository;
import com.example.nutridiary.model.FitnessActivity;
import com.example.nutridiary.model.Meal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewFitnessActivityActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editActivityType;
    private EditText editDuration;
    private Button buttonSave;
    private FitnessActivity fitnessActivityToEdit;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_activity_form_layout);

        editTitle = findViewById(R.id.edit_text_title);
        editActivityType = findViewById(R.id.edit_text_activity_type);
        editDuration = findViewById(R.id.edit_text_duration);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Retrieve the meal data from the extras
            int fitnessActivityId = extras.getInt("fitnessActivityId");
            String title = extras.getString("title");
            String activityType = extras.getString("activityType");
            int duration = extras.getInt("duration");

            // Create a meal object using the retrieved data
            fitnessActivityToEdit = new FitnessActivity((int) fitnessActivityId, title, activityType, duration);

            // Prefill the fields with the meal data
            editTitle.setText(title);
            editActivityType.setText(activityType);
            editDuration.setText(String.valueOf(duration));
        }

        repository = new Repository(this);

        buttonSave = findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFitnessActivity();
            }
        });
    }

    private void saveFitnessActivity() {
        String title = editTitle.getText().toString().trim();
        String activityType = editActivityType.getText().toString().trim();
        int duration = Integer.parseInt(editDuration.getText().toString().trim());

        // Check if all required fields are filled
        if (title.isEmpty() || activityType.isEmpty() || duration <= 0) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FitnessActivity fitnessActivity = new FitnessActivity(title, activityType, duration);

        // Check if this is a new meal or an existing one to update
        if (getIntent().hasExtra("fitnessActivityId")) {
            // Update existing meal
            int fitnessActivityId = getIntent().getIntExtra("fitnessActivityId", -1);
            fitnessActivity.setId(fitnessActivityId);
            repository.updateFitnessActivity(fitnessActivity);
            Toast.makeText(this, "Meal updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Save new meal
            repository.addFitnessActivity(fitnessActivity);
            Toast.makeText(this, "Meal saved successfully", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}