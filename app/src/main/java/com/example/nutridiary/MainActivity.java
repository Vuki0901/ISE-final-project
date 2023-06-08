package com.example.nutridiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.nutridiary.ui.fitness_activities.FitnessActivitiesFragment;
import com.example.nutridiary.ui.meals.MealsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nutridiary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FrameLayout contentFrame;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentFrame = findViewById(R.id.frame_layout_content);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_meals:
                        displayFragment(new MealsFragment());
                        return true;
                    case R.id.menu_fitness_activities:
                        displayFragment(new FitnessActivitiesFragment());
                        return true;
                }
                return false;
            }
        });

        // Display the MealsFragment as the default fragment
        displayFragment(new MealsFragment());
    }

    private void displayFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_content, fragment);
        transaction.commit();
    }

}