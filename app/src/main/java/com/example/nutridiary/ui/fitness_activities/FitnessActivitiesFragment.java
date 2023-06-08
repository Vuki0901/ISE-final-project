package com.example.nutridiary.ui.fitness_activities;

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
import com.example.nutridiary.model.FitnessActivity;
import com.example.nutridiary.model.Meal;
import com.example.nutridiary.ui.meals.MealAdapter;
import com.example.nutridiary.ui.meals.NewMealActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FitnessActivitiesFragment extends Fragment implements FitnessActivityAdapter.OnItemClickListener, RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    private FitnessActivityAdapter adapter;
    private FloatingActionButton fabAddActivity;
    private GestureDetector gestureDetector;

    private Repository repository;

    public FitnessActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_activity, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_fitness_activities);
        fabAddActivity = view.findViewById(R.id.fab_add_activity);
        fabAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFitnessActivityForm();
            }
        });

        repository = new Repository(getContext());

        // Set up the RecyclerView with the FitnessActivityAdapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FitnessActivityAdapter();
        recyclerView.setAdapter(adapter);

        loadFitnessActivities();

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
        adapter.setOnItemClickListener(new FitnessActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FitnessActivity fitnessActivity) {
                Intent intent = new Intent(getActivity(), NewFitnessActivityActivity.class);
                intent.putExtra("fitnessActivityId", fitnessActivity.getId()); // Pass the meal ID or any identifier
                intent.putExtra("title", fitnessActivity.getTitle());
                intent.putExtra("activityType", fitnessActivity.getActivityType());
                intent.putExtra("duration", fitnessActivity.getDuration());

                startActivity(intent);
            }
        });

        return view;
    }

    private void loadFitnessActivities() {
        // Load fitness activities from the data source and update the RecyclerView
        List<FitnessActivity> activities = repository.getAllFitnessActivities();
        adapter.setFitnessActivities(activities);
    }

    private void openNewFitnessActivityForm() {
        Intent intent = new Intent(getContext(), NewFitnessActivityActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(FitnessActivity fitnessActivity) {
        Intent intent = new Intent(getActivity(), NewFitnessActivityActivity.class);
        intent.putExtra("fitnessActivityId", fitnessActivity.getId()); // Pass the meal ID or any identifier
        intent.putExtra("title", fitnessActivity.getTitle());
        intent.putExtra("activityType", fitnessActivity.getActivityType());
        intent.putExtra("duration", fitnessActivity.getDuration());

        startActivity(intent);
    }

    private void showDeleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Obriši unos tjelesne aktivnosti")
                .setMessage("Jeste li sigurni da želite obrisati ovaj unos tjelesne aktivnosti?")
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
        FitnessActivity fitnessActivityToDelete = repository.getAllFitnessActivities().get(position);

        // Delete the meal from the data source
        repository.deleteFitnessActivity(fitnessActivityToDelete);

        loadFitnessActivities();

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

