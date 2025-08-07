package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        TextView totalWaterText = findViewById(R.id.totalWaterText);
        EditText addWaterInput = findViewById(R.id.addWaterInput);
        Button addWaterButton = findViewById(R.id.addWaterButton);
        Button homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        int totalWater = Main.getTotalWaterToday();
        totalWaterText.setText(totalWater + " oz");

        TextView waterMessage = findViewById(R.id.waterMessage);
        updateWaterGoalMessage(waterMessage, totalWater);

        addWaterButton.setOnClickListener(v -> {
            String waterStr = addWaterInput.getText().toString();

            //validation
            if (waterStr.isEmpty()) {
                Toast.makeText(this, "Please enter amount of water", Toast.LENGTH_SHORT).show();
                return;
            }

            int ounces;
            try {
                ounces = Integer.parseInt(waterStr);
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


            int newTotal = Main.getTotalWaterToday();
            totalWaterText.setText(newTotal + " oz");
            updateWaterGoalMessage(waterMessage, newTotal);
        });

        Button foodButton = findViewById(R.id.foodButton);
        Button sleepButton = findViewById(R.id.sleepButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));

    }

    private void updateWaterGoalMessage(TextView waterMessage, int totalWater) {
        if (totalWater >= Main.getUserGoals().getWaterGoalOz()) {
            waterMessage.setText("Great job! You met your water goal!");
        } else {
            waterMessage.setText("You haven't met your water goal yet!");
        }
    }
}