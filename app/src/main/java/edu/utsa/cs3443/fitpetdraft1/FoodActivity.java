package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        TextView currentCaloriesText = findViewById(R.id.currentCaloriesText);
        TextView caloriesLeftText = findViewById(R.id.caloriesLeftText);
        EditText foodNameInput = findViewById(R.id.foodNameInput);
        EditText caloriesInput = findViewById(R.id.caloriesInput);
        EditText fatsInput = findViewById(R.id.fatsInput);
        EditText carbsInput = findViewById(R.id.carbsInput);
        EditText proteinInput = findViewById(R.id.proteinInput);
        Button saveFoodButton = findViewById(R.id.saveFoodButton);
        Button homeButton = findViewById(R.id.homeButton);


        int currentCalories = Main.getTotalCaloriesToday();
        int goalCalories = Main.getUserGoals().getFoodGoalCalories();
        int caloriesLeft = goalCalories - currentCalories;

        currentCaloriesText.setText("Current Calories: " + currentCalories);
        caloriesLeftText.setText("Calories Left: " + caloriesLeft);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        saveFoodButton.setOnClickListener(v -> {
            String name = foodNameInput.getText().toString();
            String caloriesStr = caloriesInput.getText().toString();
            String fatsStr = fatsInput.getText().toString();
            String carbsStr = carbsInput.getText().toString();
            String proteinStr = proteinInput.getText().toString();

            int calories = caloriesStr.isEmpty() ? 0 : Integer.parseInt(caloriesStr);
            int fats = fatsStr.isEmpty() ? 0 : Integer.parseInt(fatsStr);
            int carbs = carbsStr.isEmpty() ? 0 : Integer.parseInt(carbsStr);
            int protein = proteinStr.isEmpty() ? 0 : Integer.parseInt(proteinStr);

            Main.addFood(name, calories, fats, carbs, protein);

            foodNameInput.setText("");
            caloriesInput.setText("");
            fatsInput.setText("");
            carbsInput.setText("");
            proteinInput.setText("");

            // update display
            int newCalories = Main.getTotalCaloriesToday();
            int newCaloriesLeft = goalCalories - newCalories;
            currentCaloriesText.setText("Current Calories: " + newCalories);
            caloriesLeftText.setText("Calories Left: " + newCaloriesLeft);
        });


        Button sleepButton = findViewById(R.id.sleepButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);
        Button waterButton = findViewById(R.id.waterButton);

        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));
    }
}