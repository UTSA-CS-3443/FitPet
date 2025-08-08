package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Food activity to log food data. The user is able to enter and save food name and calories.
 * Optionally user is able to save fats, proteins and carbs.
 * Displays functional navigational tools to Exercise, Sleep, Water and Home screens
 * Validates user input for more realistic input values
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class FoodActivity extends AppCompatActivity {

    private TextView currentCaloriesText;
    private TextView caloriesLeftText;
    private EditText foodNameInput, caloriesInput, fatsInput, carbsInput, proteinInput;

    /**
     * Initializes food screen
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        currentCaloriesText = findViewById(R.id.currentCaloriesText);
        caloriesLeftText    = findViewById(R.id.caloriesLeftText);
        foodNameInput       = findViewById(R.id.foodNameInput);
        caloriesInput       = findViewById(R.id.caloriesInput);
        fatsInput           = findViewById(R.id.fatsInput);
        carbsInput          = findViewById(R.id.carbsInput);
        proteinInput        = findViewById(R.id.proteinInput);
        Button saveFoodButton = findViewById(R.id.saveFoodButton);
        Button homeButton     = findViewById(R.id.homeButton);

        updateCalorieHeader();
        // Navigates back to home screen
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Saves food
        saveFoodButton.setOnClickListener(v -> {
            String name       = foodNameInput.getText().toString().trim();
            String caloriesStr= caloriesInput.getText().toString().trim();
            String fatsStr    = fatsInput.getText().toString().trim();
            String carbsStr   = carbsInput.getText().toString().trim();
            String proteinStr = proteinInput.getText().toString().trim();

            // Food validation
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
            int calories, fats = 0, carbs = 0, protein = 0;
            try {
                calories = Integer.parseInt(caloriesStr);
            } catch (NumberFormatException e) {
                caloriesInput.setError("Invalid calories input");
                Toast.makeText(this, "Invalid calories input", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if (!fatsStr.isEmpty())    fats = Integer.parseInt(fatsStr);
                if (!carbsStr.isEmpty())   carbs = Integer.parseInt(carbsStr);
                if (!proteinStr.isEmpty()) protein = Integer.parseInt(proteinStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid macro input", Toast.LENGTH_SHORT).show();
                return;
            }

            // Macro validation
            boolean macrosEntered = (!fatsStr.isEmpty() || !carbsStr.isEmpty() || !proteinStr.isEmpty());
            if (macrosEntered) {
                boolean macroError = false;

                int maxFat     = (int) Math.ceil(calories / 9.0);
                int maxCarbs   = (int) Math.ceil(calories / 4.0);
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

            // Adds food to log
            if (!macrosEntered) {
                Main.addFood(name, calories);
            } else {
                Main.addFood(name, calories, fats, carbs, protein);
            }

            Toast.makeText(this, "Food added successfully!", Toast.LENGTH_SHORT).show();

            // Clear all inputs
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
            updateCalorieHeader();
        });

        // Navigation buttons
        Button sleepButton = findViewById(R.id.sleepButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);
        Button waterButton = findViewById(R.id.waterButton);

        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));
    }

    /**
     * Refreshes calorie information
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateCalorieHeader();
    }

    /**
     * Updates calorie headers
     */
    private void updateCalorieHeader() {
        int eaten = Main.getTotalCaloriesToday();
        int left  = Main.getCaloriesLeftToday();
        currentCaloriesText.setText("Current Calories: " + eaten);
        caloriesLeftText.setText("Calories Left: " + left);
    }
}
