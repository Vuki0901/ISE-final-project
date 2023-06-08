package com.example.nutridiary.ui.meals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutridiary.R;
import com.example.nutridiary.model.Meal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;
    private OnItemClickListener listener;

    public MealAdapter() {
        this.meals = new ArrayList<>();
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a ViewHolder
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        // Bind the meal data to the ViewHolder
        Meal meal = meals.get(position);
        holder.bind(meal);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Meal meal);
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDishName;
        private TextView textViewCalorieCount;
        private TextView textViewDate;
        private TextView textViewMealCategory;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDishName = itemView.findViewById(R.id.text_view_dish_name);
            textViewCalorieCount = itemView.findViewById(R.id.text_view_calorie_count);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewMealCategory = itemView.findViewById(R.id.text_view_meal_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(meals.get(getAdapterPosition()));
                    }
                }
            });
        }

        public void bind(Meal meal) {
            Date date = meal.getDate();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");

            // Bind the meal data to the views
            textViewDishName.setText(meal.getDishName());
            textViewCalorieCount.setText(String.valueOf(meal.getCalorieCount()) + " kcal");
            textViewDate.setText(dateFormat.format(date));
            textViewMealCategory.setText(meal.getMealCategory());
        }
    }
}
