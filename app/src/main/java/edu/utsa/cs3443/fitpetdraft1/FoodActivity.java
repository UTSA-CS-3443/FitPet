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


            if (name.isEmpty()) {
                foodNameInput.setError("Please enter a food name");
                Toast.makeText(this, "Please enter a food name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (caloriesStr.isEmpty()) {
                caloriesInput.setError("Please enter calories");
                Toast.makeText(this, "Please enter calories", Toast.LENGTH_SHORT).show();
                return;
            }

            int calories = 0;
            int fats = 0;
            int carbs = 0;
            int protein = 0;

            try {
                calories = Integer.parseInt(caloriesStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid calories input", Toast.LENGTH_SHORT).show();
                return;
            }


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




            boolean macrosEntered = (!fatsStr.isEmpty() || !carbsStr.isEmpty() || !proteinStr.isEmpty());

            if (macrosEntered) {
                boolean macroError = false;

                int maxFat = (int) Math.ceil(calories / 9.0);
                int maxCarbs = (int) Math.ceil(calories / 4.0);
                int maxProtein = (int) Math.ceil(calories / 4.0);

                if (fats > maxFat) {
                    fatsInput.setError("Too much fat for " + calories + " cal (max ~" + maxFat + "g)");
                    macroError = true;
                }
                if (carbs > maxCarbs) {
                    carbsInput.setError("Too many carbs for " + calories + " cal (max ~" + maxCarbs + "g)");
                    macroError = true;
                }
                if (protein > maxProtein) {
                    proteinInput.setError("Too much protein for " + calories + " cal (max ~" + maxProtein + "g)");
                    macroError = true;
                }

                if (macroError) {
                    Toast.makeText(this, "Macros exceed what's possible for the entered calories.", Toast.LENGTH_LONG).show();
                    return;
                }

                int macroCal = (fats * 9) + (carbs * 4) + (protein * 4);
                if (Math.abs(macroCal - calories) > 10) {
                    caloriesInput.setError("Calories don't match macros (~" + macroCal + " cal)");
                    Toast.makeText(this, "Macros do not match calories (+-10 calories allowed).", Toast.LENGTH_LONG).show();
                    return;
                }
            }



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
            foodNameInput.setError(null);
            caloriesInput.setError(null);
            fatsInput.setError(null);
            carbsInput.setError(null);
            proteinInput.setError(null);

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