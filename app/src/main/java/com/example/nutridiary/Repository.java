package com.example.nutridiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nutridiary.model.FitnessActivity;
import com.example.nutridiary.model.Meal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repository extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyAppDatabase";

    // Table names
    private static final String TABLE_MEAL = "meal";
    private static final String TABLE_FITNESS_ACTIVITY = "fitness_activity";

    // Common column names
    private static final String KEY_ID = "id";

    // Meal table column names
    private static final String KEY_MEAL_DISH_NAME = "dish_name";
    private static final String KEY_MEAL_CALORIE_COUNT = "calorie_count";
    private static final String KEY_MEAL_DATE = "date";
    private static final String KEY_MEAL_CATEGORY = "meal_category";

    // FitnessActivity table column names
    private static final String KEY_FITNESS_ACTIVITY_TITLE = "title";
    private static final String KEY_FITNESS_ACTIVITY_TYPE = "activity_type";
    private static final String KEY_FITNESS_ACTIVITY_DURATION = "duration";

    public Repository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the meal table
        String createMealTableQuery = "CREATE TABLE " + TABLE_MEAL + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_MEAL_DISH_NAME + " TEXT,"
                + KEY_MEAL_CALORIE_COUNT + " INTEGER,"
                + KEY_MEAL_DATE + " INTEGER,"
                + KEY_MEAL_CATEGORY + " TEXT"
                + ")";
        db.execSQL(createMealTableQuery);

        // Create the fitness activity table
        String createFitnessActivityTableQuery = "CREATE TABLE " + TABLE_FITNESS_ACTIVITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_FITNESS_ACTIVITY_TITLE + " TEXT,"
                + KEY_FITNESS_ACTIVITY_TYPE + " TEXT,"
                + KEY_FITNESS_ACTIVITY_DURATION + " INTEGER"
                + ")";
        db.execSQL(createFitnessActivityTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FITNESS_ACTIVITY);

        // Create new tables
        onCreate(db);
    }

    // CRUD operations for Meal

    public void addMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MEAL_DISH_NAME, meal.getDishName());
        values.put(KEY_MEAL_CALORIE_COUNT, meal.getCalorieCount());
        values.put(KEY_MEAL_DATE, meal.getDate().getTime());
        values.put(KEY_MEAL_CATEGORY, meal.getMealCategory());
        db.insert(TABLE_MEAL, null, values);
        db.close();
    }

    public Meal getMeal(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEAL, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Meal meal = null;
        if (cursor != null && cursor.moveToFirst()) {
            int dishNameIndex = cursor.getColumnIndex(KEY_MEAL_DISH_NAME);
            int calorieCountIndex = cursor.getColumnIndex(KEY_MEAL_CALORIE_COUNT);
            int dateIndex = cursor.getColumnIndex(KEY_MEAL_DATE);
            int mealCategoryIndex = cursor.getColumnIndex(KEY_MEAL_CATEGORY);

            if (dishNameIndex >= 0 && calorieCountIndex >= 0 && dateIndex >= 0 && mealCategoryIndex >= 0) {
                String dishName = cursor.getString(dishNameIndex);
                int calorieCount = cursor.getInt(calorieCountIndex);
                long dateInMillis = cursor.getLong(dateIndex);
                String mealCategory = cursor.getString(mealCategoryIndex);
                Date date = new Date(dateInMillis);
                meal = new Meal(id, dishName, calorieCount, date, mealCategory);
            }
            cursor.close();
        }
        db.close();
        return meal;
    }

    public List<Meal> getAllMeals() {
        List<Meal> mealList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MEAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int dishNameIndex = cursor.getColumnIndex(KEY_MEAL_DISH_NAME);
            int calorieCountIndex = cursor.getColumnIndex(KEY_MEAL_CALORIE_COUNT);
            int dateIndex = cursor.getColumnIndex(KEY_MEAL_DATE);
            int mealCategoryIndex = cursor.getColumnIndex(KEY_MEAL_CATEGORY);

            while (!cursor.isAfterLast() && idIndex >= 0 && dishNameIndex >= 0 && calorieCountIndex >= 0 && dateIndex >= 0 && mealCategoryIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String dishName = cursor.getString(dishNameIndex);
                int calorieCount = cursor.getInt(calorieCountIndex);
                long dateInMillis = cursor.getLong(dateIndex);
                String mealCategory = cursor.getString(mealCategoryIndex);
                Date date = new Date(dateInMillis);
                Meal meal = new Meal(id, dishName, calorieCount, date, mealCategory);
                mealList.add(meal);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return mealList;
    }

    public int updateMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MEAL_DISH_NAME, meal.getDishName());
        values.put(KEY_MEAL_CALORIE_COUNT, meal.getCalorieCount());
        values.put(KEY_MEAL_DATE, meal.getDate().getTime());
        values.put(KEY_MEAL_CATEGORY, meal.getMealCategory());
        int rowsAffected = db.update(TABLE_MEAL, values, KEY_ID + "=?", new String[]{String.valueOf(meal.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteMeal(Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEAL, KEY_ID + "=?", new String[]{String.valueOf(meal.getId())});
        db.close();
    }

    // CRUD operations for FitnessActivity

    public void addFitnessActivity(FitnessActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FITNESS_ACTIVITY_TITLE, activity.getTitle());
        values.put(KEY_FITNESS_ACTIVITY_TYPE, activity.getActivityType());
        values.put(KEY_FITNESS_ACTIVITY_DURATION, activity.getDuration());
        db.insert(TABLE_FITNESS_ACTIVITY, null, values);
        db.close();
    }

    public FitnessActivity getFitnessActivity(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FITNESS_ACTIVITY, null, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        FitnessActivity activity = null;
        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex(KEY_FITNESS_ACTIVITY_TITLE);
            int activityTypeIndex = cursor.getColumnIndex(KEY_FITNESS_ACTIVITY_TYPE);
            int durationIndex = cursor.getColumnIndex(KEY_FITNESS_ACTIVITY_DURATION);

            if (titleIndex >= 0 && activityTypeIndex >= 0 && durationIndex >= 0) {
                String title = cursor.getString(titleIndex);
                String activityType = cursor.getString(activityTypeIndex);
                int duration = cursor.getInt(durationIndex);
                activity = new FitnessActivity(id, title, activityType, duration);
            }
            cursor.close();
        }
        db.close();
        return activity;
    }

    public List<FitnessActivity> getAllFitnessActivities() {
        List<FitnessActivity> activityList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FITNESS_ACTIVITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int titleIndex = cursor.getColumnIndex(KEY_FITNESS_ACTIVITY_TITLE);
            int activityTypeIndex = cursor.getColumnIndex(KEY_FITNESS_ACTIVITY_TYPE);
            int durationIndex = cursor.getColumnIndex(KEY_FITNESS_ACTIVITY_DURATION);

            while (!cursor.isAfterLast() && idIndex >= 0 && titleIndex >= 0 && activityTypeIndex >= 0 && durationIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String activityType = cursor.getString(activityTypeIndex);
                int duration = cursor.getInt(durationIndex);
                FitnessActivity activity = new FitnessActivity(id, title, activityType, duration);
                activityList.add(activity);
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return activityList;
    }

    public int updateFitnessActivity(FitnessActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FITNESS_ACTIVITY_TITLE, activity.getTitle());
        values.put(KEY_FITNESS_ACTIVITY_TYPE, activity.getActivityType());
        values.put(KEY_FITNESS_ACTIVITY_DURATION, activity.getDuration());
        int rowsAffected = db.update(TABLE_FITNESS_ACTIVITY, values, KEY_ID + "=?", new String[]{String.valueOf(activity.getId())});
        db.close();
        return rowsAffected;
    }

    public void deleteFitnessActivity(FitnessActivity activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FITNESS_ACTIVITY, KEY_ID + "=?", new String[]{String.valueOf(activity.getId())});
        db.close();
    }
}
