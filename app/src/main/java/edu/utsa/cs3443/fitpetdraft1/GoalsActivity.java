package edu.utsa.cs3443.fitpetdraft1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

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

            int calorieGoal = calorieStr.isEmpty() ? 2000 : Integer.parseInt(calorieStr);
            int waterGoal = waterStr.isEmpty() ? 64 : Integer.parseInt(waterStr);
            int sleepGoal = sleepStr.isEmpty() ? 8 : Integer.parseInt(sleepStr);
            int exerciseGoal = 300;

            if (petName.isEmpty()) {
                petName = "Zuzu";
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("petName", petName);
            editor.putInt("calorieGoal", calorieGoal);
            editor.putInt("waterGoal", waterGoal);
            editor.putInt("sleepGoal", sleepGoal);
            editor.putInt("exerciseGoal", exerciseGoal);
            editor.apply();

            Main.initializePet(petName);
            Main.initializeUserGoals(waterGoal, sleepGoal, exerciseGoal, calorieGoal);


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
            } catch (IOException e) {
                throw new RuntimeException(e);
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
