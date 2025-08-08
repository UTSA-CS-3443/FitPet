package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Exercise activity to log exercise data. The user is able to enter and save exercise type, duration and calories burned for the day
 * Displays functional navigational tools to Food, Sleep, Water and Home screens
 * Validates user input for more realistic input values
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class ExerciseActivity extends AppCompatActivity {

    /**
     * Initializes exercise screen
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        TextView caloriesMessage = findViewById(R.id.caloriesMessage);
        EditText typeInput = findViewById(R.id.typeInput);
        EditText durationInput = findViewById(R.id.durationInput);
        EditText caloriesInput = findViewById(R.id.caloriesInput);
        Button enterButton = findViewById(R.id.enterButton);
        Button homeButton = findViewById(R.id.homeButton);


        int totalCalories = Main.getTotalExerciseToday();
        caloriesMessage.setText("You have burned " + totalCalories + " calories today.");

        // Back to home screen
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
        enterButton.setOnClickListener(v -> {
            String type = typeInput.getText().toString().trim();
            String durationStr = durationInput.getText().toString().trim();
            String caloriesStr = caloriesInput.getText().toString().trim();

            // Input validation
            if (type.isEmpty()) {
                Toast.makeText(this, "Please enter exercise type", Toast.LENGTH_SHORT).show();
                return;
            }
            if (durationStr.isEmpty()) {
                Toast.makeText(this, "Please enter duration", Toast.LENGTH_SHORT).show();
                return;
            }
            if (caloriesStr.isEmpty()) {
                Toast.makeText(this, "Please enter calories burned", Toast.LENGTH_SHORT).show();
                return;
            }
            int duration;
            int calories;
            try {
                duration = Integer.parseInt(durationStr);
                if (duration >= 1440) {
                    Toast.makeText(this, "Duration cannot exceed 24 hours", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid duration input", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                calories = Integer.parseInt(caloriesStr);
                if (calories >= 5000) {
                    Toast.makeText(this, "Calories burned too seem too high", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid calories burned input", Toast.LENGTH_SHORT).show();
                return;
            }

            // Adds exercise calories
            Main.addExercise(type + " (" + duration + " mins)", calories);
            Toast.makeText(this, "Exercise added successfully!", Toast.LENGTH_SHORT).show();

            // Clears previous inputs
            typeInput.setText("");
            durationInput.setText("");
            caloriesInput.setText("");

            // Displays updated calories burned
            int newTotal = Main.getTotalExerciseToday();
            caloriesMessage.setText("You have burned " + newTotal + " calories today.");
        });

        //Bottom navigation buttons
        Button foodButton = findViewById(R.id.foodButton);
        Button sleepButton = findViewById(R.id.sleepButton);
        Button waterButton = findViewById(R.id.waterButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));

    }
}