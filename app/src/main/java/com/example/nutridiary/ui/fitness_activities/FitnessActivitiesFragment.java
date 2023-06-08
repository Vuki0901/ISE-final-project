package com.example.nutridiary.ui.fitness_activities;

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
import com.example.nutridiary.databinding.FragmentFitnessActivityBinding;
import com.example.nutridiary.model.FitnessActivity;
import com.example.nutridiary.ui.meals.NewMealActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FitnessActivitiesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FitnessActivityAdapter adapter;
    private FloatingActionButton fabAddActivity;

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

        // Load fitness activities from the data source and update the RecyclerView
        List<FitnessActivity> activities = repository.getAllFitnessActivities();
        adapter.setFitnessActivities(activities);

        // Set click listener for items in the RecyclerView
        adapter.setOnItemClickListener(new FitnessActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FitnessActivity activity) {
                // Handle item click event (e.g., show details, edit, delete)
                Toast.makeText(getContext(), "Clicked on fitness activity: " + activity.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void openNewFitnessActivityForm() {
        Intent intent = new Intent(getContext(), NewFitnessActivityActivity.class);
        startActivity(intent);
    }
}

