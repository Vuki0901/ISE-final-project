package com.example.nutridiary.ui.fitness_activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutridiary.R;
import com.example.nutridiary.model.FitnessActivity;

import java.util.ArrayList;
import java.util.List;

public class FitnessActivityAdapter extends RecyclerView.Adapter<FitnessActivityAdapter.FitnessActivityViewHolder> {

    private List<FitnessActivity> fitnessActivities;
    private OnItemClickListener listener;

    public FitnessActivityAdapter() {
        this.fitnessActivities = new ArrayList<>();
    }

    public void setFitnessActivities(List<FitnessActivity> fitnessActivities) {
        this.fitnessActivities = fitnessActivities;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public FitnessActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fitness_activity, parent, false);
        return new FitnessActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FitnessActivityViewHolder holder, int position) {
        // Bind the fitness activity data to the ViewHolder
        FitnessActivity fitnessActivity = fitnessActivities.get(position);
        holder.bind(fitnessActivity);
    }

    @Override
    public int getItemCount() {
        return fitnessActivities.size();
    }

    public interface OnItemClickListener {
        void onItemClick(FitnessActivity activity);
    }

    public class FitnessActivityViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewActivityType;
        private TextView textViewDuration;

        public FitnessActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewActivityType = itemView.findViewById(R.id.text_view_activity_type);
            textViewDuration = itemView.findViewById(R.id.text_view_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(fitnessActivities.get(getAdapterPosition()));
                    }
                }
            });
        }

        public void bind(FitnessActivity fitnessActivity) {
            // Bind the fitness activity data to the views
            textViewTitle.setText(fitnessActivity.getTitle());
            textViewActivityType.setText(fitnessActivity.getActivityType());
            textViewDuration.setText(String.valueOf(fitnessActivity.getDuration()));
        }
    }
}


