package com.example.nutridiary.ui.meals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class MealsFragment extends Fragment implements MealAdapter.OnItemClickListener, RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private FloatingActionButton fabAddMeal;
    private Repository repository;
    private GestureDetector gestureDetector;

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

        loadMeals();

        // Create a GestureDetector to detect long press events
        gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                // Find the long pressed item position
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && getActivity() != null) {
                    int position = recyclerView.getChildAdapterPosition(childView);
                    showDeleteConfirmationDialog(position);
                }
            }
        });

        // Set the GestureDetector as the touch listener for the RecyclerView
        recyclerView.addOnItemTouchListener(this);


        // Set click listener for items in the RecyclerView
        adapter.setOnItemClickListener(new MealAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Meal meal) {
                // Handle item click event (e.g., show details, edit, delete)
                Intent intent = new Intent(getActivity(), NewMealActivity.class);
                intent.putExtra("mealId", meal.getId()); // Pass the meal ID or any identifier
                intent.putExtra("dishName", meal.getDishName());
                intent.putExtra("calorieCount", meal.getCalorieCount());
                intent.putExtra("date", meal.getDate());
                intent.putExtra("mealCategory", meal.getMealCategory());

                startActivity(intent);
            }
        });

        return view;
    }

    public void loadMeals() {
        // Load meals from the data source and update the RecyclerView
        List<Meal> meals = repository.getAllMeals();
        adapter.setMeals(meals);
    }

    private void openNewMealForm() {
        Intent intent = new Intent(getContext(), NewMealActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Meal meal) {
        Intent intent = new Intent(getActivity(), NewMealActivity.class);
        intent.putExtra("mealId", meal.getId()); // Pass the meal ID or any identifier
        intent.putExtra("dishName", meal.getDishName());
        intent.putExtra("calorieCount", meal.getCalorieCount());
        intent.putExtra("date", meal.getDate());
        intent.putExtra("mealCategory", meal.getMealCategory());

        startActivity(intent);
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Obriši unos obroka")
                .setMessage("Jeste li sigurni da želite obrisati unos obroka?")
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMeal(position);
                    }
                })
                .setNegativeButton("Ne", null)
                .show();
    }

    private void deleteMeal(int position) {
        // Get the meal at the specified position
        Meal mealToDelete = repository.getAllMeals().get(position);

        // Delete the meal from the data source
        repository.deleteMeal(mealToDelete);

        loadMeals();

        // Notify the adapter that the item was removed
        adapter.notifyItemRemoved(position);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}