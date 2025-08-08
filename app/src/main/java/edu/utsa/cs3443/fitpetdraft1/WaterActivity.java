package edu.utsa.cs3443.fitpetdraft1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Water activity to log water entries.
 * Allows the user to add ounces of water and see their progress
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class WaterActivity extends AppCompatActivity {

    /**
     * Initializes water screen
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        TextView totalWaterText = findViewById(R.id.totalWaterText);
        TextView waterMessage   = findViewById(R.id.waterMessage);
        EditText addWaterInput  = findViewById(R.id.addWaterInput);
        Button addWaterButton   = findViewById(R.id.addWaterButton);
        Button homeButton       = findViewById(R.id.homeButton);

        //Back to home screen
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        updateWaterProgress(totalWaterText);
        updateWaterGoalMessage(waterMessage, Main.getTotalWaterToday());

        // Add water button
        addWaterButton.setOnClickListener(v -> {
            String waterStr = addWaterInput.getText().toString().trim();

            //validation
            if (waterStr.isEmpty()) {
                addWaterInput.setError("Enter ounces");
                Toast.makeText(this, "Please enter amount of water", Toast.LENGTH_SHORT).show();
                return;
            }
            int ounces;
            try {
                ounces = Integer.parseInt(waterStr);
                if (ounces <= 0) {
                    addWaterInput.setError("Must be > 0");
                    return;
                }
                if (ounces > 500) {
                    Toast.makeText(this, "Water amount too high", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                addWaterInput.setError("Invalid water input");
                return;
            }

            Main.addWater(ounces);
            Toast.makeText(this, "Water added successfully!", Toast.LENGTH_SHORT).show();
            addWaterInput.setText("");
            addWaterInput.setError(null);

            updateWaterProgress(totalWaterText);
            updateWaterGoalMessage(waterMessage, Main.getTotalWaterToday());
        });

        // Navigation buttons
        Button foodButton = findViewById(R.id.foodButton);
        Button sleepButton = findViewById(R.id.sleepButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
    }

    /**
     * Updates the progress display
     * @param totalWaterText the total water to display
     */
    private void updateWaterProgress(TextView totalWaterText) {
        int total = Main.getTotalWaterToday();
        int goal  = Main.getUserGoals().getWaterGoalOz();
        totalWaterText.setText(total + " / " + goal + " oz");
    }

    /**
     * Updates the message indicating whether water goal has been met or not.
     * @param waterMessage the TextView to udpate
     * @param totalWater the total amount of water
     */
    private void updateWaterGoalMessage(TextView waterMessage, int totalWater) {
        if (totalWater >= Main.getUserGoals().getWaterGoalOz()) {
            waterMessage.setText("Great job! You met your water goal!");
        } else {
            waterMessage.setText("You haven't met your water goal yet!");
        }
    }
}
