package com.example.nutridiary.ui.meals;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutridiary.R;
import com.example.nutridiary.Repository;
import com.example.nutridiary.model.Meal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewMealActivity extends AppCompatActivity {

    private EditText editTextDishName;
    private EditText editTextCalorieCount;
    private TextView textViewDateLabel;
    private Button buttonPickDate;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private EditText editTextMealCategory;
    private Button buttonSave;
    private Date selectedDate;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_form_layout);

        editTextDishName = findViewById(R.id.edit_text_dish_name);
        editTextCalorieCount = findViewById(R.id.edit_text_calorie_count);
        textViewDateLabel = findViewById(R.id.text_view_date_label);
        buttonPickDate = findViewById(R.id.button_pick_date);
        editTextMealCategory = findViewById(R.id.edit_text_meal_category);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        buttonSave = findViewById(R.id.button_save);

        repository = new Repository(this);

        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMeal();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                selectedDate = calendar.getTime();

                String formattedDate = dateFormat.format(selectedDate);
                textViewDateLabel.setText(formattedDate);
            }
        };

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.show();
    }

    private void saveMeal() {
        String dishName = editTextDishName.getText().toString().trim();
        int calorieCount = Integer.parseInt(editTextCalorieCount.getText().toString().trim());
        Date date = selectedDate;
        String mealCategory = editTextMealCategory.getText().toString().trim();

        // Check if all required fields are filled
        if (dishName.isEmpty() || date == null || mealCategory.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Meal meal = new Meal(dishName, calorieCount, date, mealCategory);

        // Check if this is a new meal or an existing one to update
        if (getIntent().hasExtra("meal_id")) {
            // Update existing meal
            int mealId = getIntent().getIntExtra("meal_id", -1);
            meal.setId(mealId);
            repository.updateMeal(meal);
            Toast.makeText(this, "Meal updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Save new meal
            repository.addMeal(meal);
            Toast.makeText(this, "Meal saved successfully", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}
