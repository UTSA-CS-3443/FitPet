package edu.utsa.cs3443.fitpetdraft1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

/**
 * Main activity is the apps home screen.
 * Displays navigational buttons on the button to allow user to input data
 * Displays settings icon that allows user to customize goals and save data for the day
 * Displays pets name, pet status and goals
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private TextView petNameText;
    private TextView goalsSummaryText;

    private Button exerciseButton, foodButton, sleepButton, waterButton, settingsButton;

    /**
     * Initializes home screen
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

    /**
     * Assigns buttons and TextViews
     */
    private void initializeViews() {
        petNameText      = findViewById(R.id.petNameText);
        goalsSummaryText = findViewById(R.id.goalsSummaryText); // NEW

        exerciseButton = findViewById(R.id.exerciseButton);
        foodButton     = findViewById(R.id.foodButton);
        waterButton    = findViewById(R.id.waterButton);
        sleepButton    = findViewById(R.id.sleepButton);
        settingsButton = findViewById(R.id.settingsButton);
    }

    /**
     * Checks whether the set goals are in shared preferences
     * @return true if goals are set, false otherwise
     */
    private boolean areGoalsSet() {
        return prefs.contains("petName") && prefs.getInt("calorieGoal", -1) != -1;
    }

    /**
     * Loads user goals and pet information
     */
    private void loadGoals() {
        String petName     = prefs.getString("petName", "Zuzu");
        int calorieGoal    = prefs.getInt("calorieGoal", 2000);
        int waterGoal      = prefs.getInt("waterGoal", 64);
        int sleepGoal      = prefs.getInt("sleepGoal", 8);
        int exerciseGoal   = prefs.getInt("exerciseGoal", 300);

        Main.initializeUserGoals(waterGoal, sleepGoal, exerciseGoal, calorieGoal);
        Main.initializePet(petName);
    }

    /**
     * Validates new day start and handles daily log reset
     * @throws IOException if file fails
     */
    private void checkForNewDay() throws IOException {
        DayManager.checkAndHandleNewDay(this);
    }

    /**
     * Sets on click listeners for navigation buttons
     */
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

    /**
     * Updates goal summary text on home page with curreny days progress
     */
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

    /**
     * Updates home page
     */
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
