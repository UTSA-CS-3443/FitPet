package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

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

            // validation
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a food name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (caloriesStr.isEmpty()) {
                Toast.makeText(this, "Please enter calories", Toast.LENGTH_SHORT).show();
                return;
            }

            int calories;
            int fats = 0;
            int carbs = 0;
            int protein = 0;

            // validate calories
            try {
                calories = Integer.parseInt(caloriesStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid calories input", Toast.LENGTH_SHORT).show();
                return;
            }

            // validate macros
            try {
                if (!fatsStr.isEmpty()) {
                    fats = Integer.parseInt(fatsStr);
                }
                if (!carbsStr.isEmpty()) {
                    carbs = Integer.parseInt(carbsStr);
                }
                if (!proteinStr.isEmpty()) {
                    protein = Integer.parseInt(proteinStr);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid macro input", Toast.LENGTH_SHORT).show();
                return;
            }

            // check if macros entered
            if (fatsStr.isEmpty() && carbsStr.isEmpty() && proteinStr.isEmpty()) {
                Main.addFood(name, calories);
            } else {
                Main.addFood(name, calories, fats, carbs, protein);
            }

            Toast.makeText(this, "Food added successfully!", Toast.LENGTH_SHORT).show();

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