package edu.utsa.cs3443.fitpetdraft1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

/**
 * Goals activity to save user goals. Prompts user to set goals before accessing other features.
 * Enable/Disables buttons until goals are set.
 * Allows the user to save their daily progress and customize their goals after initialization.
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class GoalsActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private Button foodButton, sleepButton, exerciseButton, waterButton, saveProgressButton;


    /**
     * Initializes goals screen
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        prefs = getSharedPreferences("FitPetPrefs", MODE_PRIVATE);

        EditText petNameInput      = findViewById(R.id.petNameInput);
        EditText calorieGoalInput  = findViewById(R.id.calorieGoalInput);
        EditText waterGoalInput    = findViewById(R.id.waterGoalInput);
        EditText sleepGoalInput    = findViewById(R.id.sleepGoalInput);
        Button enterButton         = findViewById(R.id.enterButton);
        saveProgressButton         = findViewById(R.id.saveProgressButton);

        foodButton     = findViewById(R.id.foodButton);
        sleepButton    = findViewById(R.id.sleepButton);
        exerciseButton = findViewById(R.id.exerciseButton);
        waterButton    = findViewById(R.id.waterButton);

        setBottomButtonsEnabled(isGoalsSet());
        setSaveProgressEnabled(isGoalsSet());

        // Population
        if (Main.getUserGoals() != null) {
            calorieGoalInput.setText(String.valueOf(Main.getUserGoals().getFoodGoalCalories()));
            waterGoalInput.setText(String.valueOf(Main.getUserGoals().getWaterGoalOz()));
            sleepGoalInput.setText(String.valueOf(Main.getUserGoals().getSleepGoalHours()));
        }
        if (Main.getPet() != null) {
            String petName = prefs.getString("petName", "Zuzu");
            if (!petName.isEmpty()) petNameInput.setText(petName);
        }

        // Save goals and navigate to home page
        enterButton.setOnClickListener(v -> {
            String petName   = petNameInput.getText().toString().trim();
            String calorieStr= calorieGoalInput.getText().toString().trim();
            String waterStr  = waterGoalInput.getText().toString().trim();
            String sleepStr  = sleepGoalInput.getText().toString().trim();


            // Validation
            if (petName.isEmpty()) {
                petNameInput.setError("Please enter a pet name");
                Toast.makeText(this, "Please enter a pet name", Toast.LENGTH_SHORT).show();
            }
            int calorieGoal = 0;
            if (calorieStr.isEmpty()) {
                calorieGoalInput.setError("Please enter calorie goal");
                Toast.makeText(this, "Please enter calorie goal", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    calorieGoal = Integer.parseInt(calorieStr);
                } catch (NumberFormatException e) {
                    calorieGoalInput.setError("Invalid calorie goal");

                }
            }
            int waterGoal = 0;
            if (waterStr.isEmpty()) {
                waterGoalInput.setError("Please enter water goal");
                Toast.makeText(this, "Please enter water goal", Toast.LENGTH_SHORT).show();
            } else {
                try { waterGoal = Integer.parseInt(waterStr); }
                catch (NumberFormatException e) {
                    waterGoalInput.setError("Invalid water goal");

                }
            }
            int sleepGoal = 0;
            if (sleepStr.isEmpty()) {
                sleepGoalInput.setError("Please enter sleep goal");
                Toast.makeText(this, "Please enter sleep goal", Toast.LENGTH_SHORT).show();
            } else {
                try { sleepGoal = Integer.parseInt(sleepStr); }
                catch (NumberFormatException e) {
                    sleepGoalInput.setError("Invalid sleep goal");

                }
            }


            int exerciseGoal = 0;

            // Save to SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("petName", petName);
            editor.putInt("calorieGoal", calorieGoal);
            editor.putInt("waterGoal", waterGoal);
            editor.putInt("sleepGoal", sleepGoal);
            editor.putInt("exerciseGoal", exerciseGoal);
            editor.apply();

            //Initialize app state
            Main.initializePet(petName);
            Main.initializeUserGoals(waterGoal, sleepGoal, exerciseGoal, calorieGoal);

            Toast.makeText(this, "Goals saved successfully!", Toast.LENGTH_SHORT).show();

            setBottomButtonsEnabled(true);
            setSaveProgressEnabled(true);

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Save progress to file
        saveProgressButton.setOnClickListener(v -> {
            try {
                DayManager.checkAndHandleNewDay(this);
                DayManager.saveCurrentDayToFile(this);
                Toast.makeText(this, "Progress saved successfully!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error saving progress", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigation Buttons
        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));
    }



    @Override
    protected void onResume() {
        super.onResume();
        setBottomButtonsEnabled(isGoalsSet());
        setSaveProgressEnabled(isGoalsSet());
    }

    /**
     * Enables/Disables the bottom navigation buttons and home screen
     * Dims the navigation buttons and home screen if disabled
     * @param enabled
     */
    private void setBottomButtonsEnabled(boolean enabled) {
        foodButton.setEnabled(enabled);
        sleepButton.setEnabled(enabled);
        exerciseButton.setEnabled(enabled);
        waterButton.setEnabled(enabled);
        float alpha;
        if(enabled){
            alpha = 1f;
        } else {
            alpha = 0.6f;
        }
        foodButton.setAlpha(alpha);
        sleepButton.setAlpha(alpha);
        exerciseButton.setAlpha(alpha);
        waterButton.setAlpha(alpha);

    }

    /**
     * Enables/disabled the save progress button and dims the button if disabled
     * @param enabled
     */
    private void setSaveProgressEnabled(boolean enabled) {
        saveProgressButton.setEnabled(enabled);

        float alpha;
        if(enabled){
            alpha = 1f;
        } else {
            alpha = 0.6f;
        }
        saveProgressButton.setAlpha(alpha);
    }

    /**
     * Check if goals have been set
     * @return true if goals are set
     */
    private boolean isGoalsSet() {
        String pet  = prefs.getString("petName", "");
        int cal     = prefs.getInt("calorieGoal", 0);
        int water   = prefs.getInt("waterGoal", 0);
        int sleep   = prefs.getInt("sleepGoal", 0);
        return !pet.isEmpty() && cal > 0 && water > 0 && sleep > 0;
    }
}

