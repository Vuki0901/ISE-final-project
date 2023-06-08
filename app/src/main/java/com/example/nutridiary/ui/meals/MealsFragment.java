package com.example.nutridiary.ui.meals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutridiary.R;
import com.example.nutridiary.Repository;
import com.example.nutridiary.databinding.FragmentMealsBinding;
import com.example.nutridiary.model.Meal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment extends Fragment implements MealAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private FloatingActionButton fabAddMeal;

    private Repository repository;

    public MealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_meals);
        fabAddMeal = view.findViewById(R.id.fab_add_meal);
        fabAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewMealForm();
            }
        });

        repository = new Repository(getContext());

        // Set up the RecyclerView with the MealAdapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MealAdapter();
        recyclerView.setAdapter(adapter);

        // Load meals from the data source and update the RecyclerView
        List<Meal> meals = repository.getAllMeals();
        adapter.setMeals(meals);

        // Set click listener for items in the RecyclerView
        adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Meal meal) {
                // Handle item click event (e.g., show details, edit, delete)
                Toast.makeText(getContext(), "Clicked on meal: " + meal.getDishName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void openNewMealForm() {
        Intent intent = new Intent(getContext(), NewMealActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Meal meal) {
        // Handle item click event (e.g., show details, edit, delete)
        Toast.makeText(getContext(), "Clicked on meal: " + meal.getDishName(), Toast.LENGTH_SHORT).show();
    }
}

