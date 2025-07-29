package edu.utsa.cs3443.fitpetdraft1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private TextView petNameText;
    private Button exerciseButton, foodButton, sleepButton, waterButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("FitPetPrefs", MODE_PRIVATE);
        initializeViews();

        // if the goals aren't set, gaol screen opens first
        if (!areGoalsSet()) {
            Intent intent = new Intent(this, GoalsActivity.class);
            startActivity(intent);
            finish();
        } else { //if they are set (returning user) , home screen opens
            loadGoals();
            try {
                checkForNewDay();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setupClickListeners();
        }
    }

    private void initializeViews() {
        petNameText = findViewById(R.id.petNameText);
        exerciseButton = findViewById(R.id.exerciseButton);
        foodButton = findViewById(R.id.foodButton);
        waterButton = findViewById(R.id.waterButton);
        sleepButton = findViewById(R.id.sleepButton);
        settingsButton = findViewById(R.id.settingsButton);

    }

    // checks if user completed initial setup by looking for saved data in prefs
    // pet name and calorie goal set
    private boolean areGoalsSet() {
        return prefs.contains("petName") && prefs.getInt("calorieGoal", -1) != -1;
    }

    private void loadGoals() {
        String petName = prefs.getString("petName", "Zuzu");
        int calorieGoal = prefs.getInt("calorieGoal", 2000);
        int waterGoal = prefs.getInt("waterGoal", 64);
        int sleepGoal = prefs.getInt("sleepGoal", 8);
        int exerciseGoal = prefs.getInt("exerciseGoal", 500);

        Main.initializeUserGoals(waterGoal, sleepGoal, exerciseGoal, calorieGoal);
        Main.initializePet(petName);

        if (petNameText != null) {
            petNameText.setText(petName);
        }
    }

    private void checkForNewDay() throws IOException {
        DayManager.checkAndHandleNewDay(this);
    }

    private void setupClickListeners() {
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                startActivity(intent);
            }
        });

        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WaterActivity.class);
                startActivity(intent);
            }
        });

        sleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SleepActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoalsActivity.class);
                startActivity(intent);
            }
        });
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
        }

        if (Main.getPet() != null && petNameText != null) {
            petNameText.setText(Main.getPet().toString());
        }
    }
}