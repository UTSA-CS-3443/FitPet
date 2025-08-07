package edu.utsa.cs3443.fitpetdraft1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class GoalsActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        prefs = getSharedPreferences("FitPetPrefs", MODE_PRIVATE);

        EditText petNameInput = findViewById(R.id.petNameInput);
        EditText calorieGoalInput = findViewById(R.id.calorieGoalInput);
        EditText waterGoalInput = findViewById(R.id.waterGoalInput);
        EditText sleepGoalInput = findViewById(R.id.sleepGoalInput);
        Button enterButton = findViewById(R.id.enterButton);
        Button saveProgressButton = findViewById(R.id.saveProgressButton);

        // set current values
        if (Main.getUserGoals() != null) {
            calorieGoalInput.setText(String.valueOf(Main.getUserGoals().getFoodGoalCalories()));
            waterGoalInput.setText(String.valueOf(Main.getUserGoals().getWaterGoalOz()));
            sleepGoalInput.setText(String.valueOf(Main.getUserGoals().getSleepGoalHours()));
        }

        if (Main.getPet() != null) {
            String petName = prefs.getString("petName", "Zuzu");
            if (!petName.isEmpty()) {
                petNameInput.setText(petName);
            }
        }

        enterButton.setOnClickListener(v -> {
            String petName = petNameInput.getText().toString();
            String calorieStr = calorieGoalInput.getText().toString();
            String waterStr = waterGoalInput.getText().toString();
            String sleepStr = sleepGoalInput.getText().toString();

            if (petName.isEmpty()) {
                petName = "Zuzu";
            }

            int calorieGoal;
            int waterGoal;
            int sleepGoal;

            // validate inputs
            if (calorieStr.isEmpty()) {
                Toast.makeText(this, "Please enter calorie goal", Toast.LENGTH_SHORT).show();
                return;
            } else {
                try {
                    calorieGoal = Integer.parseInt(calorieStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid calorie goal", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (waterStr.isEmpty()) {
                Toast.makeText(this, "Please enter water goal", Toast.LENGTH_SHORT).show();
                return;
            } else {
                try {
                    waterGoal = Integer.parseInt(waterStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid water goal", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (sleepStr.isEmpty()) {
                Toast.makeText(this, "Please enter sleep goal", Toast.LENGTH_SHORT).show();
                return;
            } else {
                try {
                    sleepGoal = Integer.parseInt(sleepStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid sleep goal", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            int exerciseGoal = 300;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("petName", petName);
            editor.putInt("calorieGoal", calorieGoal);
            editor.putInt("waterGoal", waterGoal);
            editor.putInt("sleepGoal", sleepGoal);
            editor.putInt("exerciseGoal", exerciseGoal);
            editor.apply();

            Main.initializePet(petName);
            Main.initializeUserGoals(waterGoal, sleepGoal, exerciseGoal, calorieGoal);
            Toast.makeText(this, "Goals saved successfully!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        saveProgressButton.setOnClickListener(v -> {
            try {
                DayManager.checkAndHandleNewDay(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                DayManager.saveCurrentDayToFile(this);
                Toast.makeText(this, "Progress saved successfully!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error saving progress", Toast.LENGTH_SHORT).show();
            }
        });

        Button foodButton = findViewById(R.id.foodButton);
        Button sleepButton = findViewById(R.id.sleepButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);
        Button waterButton = findViewById(R.id.waterButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));
    }
}
