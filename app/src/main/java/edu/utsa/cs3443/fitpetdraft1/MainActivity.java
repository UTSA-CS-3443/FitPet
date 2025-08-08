package edu.utsa.cs3443.fitpetdraft1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private TextView petNameText;
    private TextView goalsSummaryText;

    private Button exerciseButton, foodButton, sleepButton, waterButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FitPetPrefs", MODE_PRIVATE);
        initializeViews();

        if (!areGoalsSet()) {
            startActivity(new Intent(this, GoalsActivity.class));
            finish();
            return;
        }

        loadGoals();
        try {
            checkForNewDay();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setupClickListeners();

        if (Main.getPet() != null) {
            petNameText.setText(Main.getPet().toString());
        }
        updateGoalsSummary();
    }

    private void initializeViews() {
        petNameText      = findViewById(R.id.petNameText);
        goalsSummaryText = findViewById(R.id.goalsSummaryText); // NEW

        exerciseButton = findViewById(R.id.exerciseButton);
        foodButton     = findViewById(R.id.foodButton);
        waterButton    = findViewById(R.id.waterButton);
        sleepButton    = findViewById(R.id.sleepButton);
        settingsButton = findViewById(R.id.settingsButton);
    }

    private boolean areGoalsSet() {
        return prefs.contains("petName") && prefs.getInt("calorieGoal", -1) != -1;
    }

    private void loadGoals() {
        String petName     = prefs.getString("petName", "Zuzu");
        int calorieGoal    = prefs.getInt("calorieGoal", 2000);
        int waterGoal      = prefs.getInt("waterGoal", 64);
        int sleepGoal      = prefs.getInt("sleepGoal", 8);
        int exerciseGoal   = prefs.getInt("exerciseGoal", 300);

        Main.initializeUserGoals(waterGoal, sleepGoal, exerciseGoal, calorieGoal);
        Main.initializePet(petName);
    }

    private void checkForNewDay() throws IOException {
        DayManager.checkAndHandleNewDay(this);
    }

    private void setupClickListeners() {
        exerciseButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ExerciseActivity.class)));
        foodButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FoodActivity.class)));
        waterButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, WaterActivity.class)));
        sleepButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SleepActivity.class)));
        settingsButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GoalsActivity.class)));
    }

    private void updateGoalsSummary() {
        UserGoals g = Main.getUserGoals();
        if (g == null) {
            if (goalsSummaryText != null) {
                goalsSummaryText.setText("Set your goals to get started.");
            }
            return;
        }

        int eaten    = Main.getTotalCaloriesToday();
        int burned   = Main.getTotalExerciseToday();
        int calGoal  = g.getFoodGoalCalories();
        int calLeft  = calGoal + burned - eaten;

        int water     = Main.getTotalWaterToday();
        int waterGoal = g.getWaterGoalOz();

        int sleep     = Main.getTotalSleepToday();
        int sleepGoal = g.getSleepGoalHours();

        String summary = "Calories: " + eaten + " / " + calGoal + " (Left: " + calLeft + ")\n" +
                "Water: " + water + " / " + waterGoal + " oz\n" +
                "Sleep: " + sleep + " / " + sleepGoal + " hrs";

        if (goalsSummaryText != null) {
            goalsSummaryText.setText(summary);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (areGoalsSet()) {
            try {
                checkForNewDay();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (Main.getPet() != null) {
                petNameText.setText(Main.getPet().toString());
            }
            updateGoalsSummary();
        }
    }
}
