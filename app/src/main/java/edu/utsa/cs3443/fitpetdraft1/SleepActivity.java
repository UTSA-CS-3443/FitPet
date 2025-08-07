package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SleepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        TextView sleepMessage = findViewById(R.id.sleepMessage);
        EditText sleepInput = findViewById(R.id.sleepInput);
        Button enterButton = findViewById(R.id.enterButton);
        Button homeButton = findViewById(R.id.homeButton);

        // update display
        int totalSleep = Main.getTotalSleepToday();
        if (totalSleep >= Main.getUserGoals().getSleepGoalHours()) {
            sleepMessage.setText("Great job! You met your sleep goal!");
        } else {
            sleepMessage.setText("You haven't met your sleep goal yet!");
        }

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // enter button
        enterButton.setOnClickListener(v -> {
            String hoursStr = sleepInput.getText().toString().trim();

            // validation
            if (hoursStr.isEmpty()) {
                Toast.makeText(this, "Please enter hours of sleep", Toast.LENGTH_SHORT).show();
                return;
            }

            int hours;
            try {
                hours = Integer.parseInt(hoursStr);
                if (hours > 24) {
                    Toast.makeText(this, "Hours cannot exceed 24", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid sleep hours", Toast.LENGTH_SHORT).show();
                return;
            }

            Main.addSleep(hours);
            Toast.makeText(this, "Sleep hours added successfully!", Toast.LENGTH_SHORT).show();
            sleepInput.setText("");

            // update display
            int newTotal = Main.getTotalSleepToday();
            if (newTotal >= Main.getUserGoals().getSleepGoalHours()) {
                sleepMessage.setText("Great job! You met your sleep goal!");
            } else {
                sleepMessage.setText("You haven't met your sleep goal yet!");
            }
        });

        // nav buttons
        Button foodButton = findViewById(R.id.foodButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);
        Button waterButton = findViewById(R.id.waterButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));

    }
}