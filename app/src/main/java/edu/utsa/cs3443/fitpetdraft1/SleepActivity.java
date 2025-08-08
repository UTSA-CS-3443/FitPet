package edu.utsa.cs3443.fitpetdraft1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SleepActivity extends AppCompatActivity {

    private TextView sleepMessage;
    private TextView totalSleepProgressText;
    private EditText sleepInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        sleepMessage = findViewById(R.id.sleepMessage);
        totalSleepProgressText = findViewById(R.id.totalSleepProgressText);
        sleepInput = findViewById(R.id.sleepInput);
        Button enterButton = findViewById(R.id.enterButton);
        Button homeButton = findViewById(R.id.homeButton);

        refreshSleepUI();

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        enterButton.setOnClickListener(v -> {
            String hoursStr = sleepInput.getText().toString().trim();

            if (hoursStr.isEmpty()) {
                sleepInput.setError("Please enter hours of sleep");
                Toast.makeText(this, "Please enter hours of sleep", Toast.LENGTH_SHORT).show();
                return;
            }

            int hours;
            try {
                hours = Integer.parseInt(hoursStr);
                if (hours <= 0) {
                    sleepInput.setError("Must be > 0");
                    return;
                }
                if (hours > 24) {
                    Toast.makeText(this, "Hours cannot exceed 24", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                sleepInput.setError("Invalid sleep hours");
                Toast.makeText(this, "Invalid sleep hours", Toast.LENGTH_SHORT).show();
                return;
            }

            Main.addSleep(hours);
            Toast.makeText(this, "Sleep hours added successfully!", Toast.LENGTH_SHORT).show();
            sleepInput.setText("");
            sleepInput.setError(null);

            refreshSleepUI();
        });

        Button foodButton = findViewById(R.id.foodButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);
        Button waterButton = findViewById(R.id.waterButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSleepUI();
    }

    private void refreshSleepUI() {
        int total = Main.getTotalSleepToday();
        int goal  = Main.getUserGoals().getSleepGoalHours();

        totalSleepProgressText.setText(total + " / " + goal + " hrs");

        if (total >= goal) {
            sleepMessage.setText("Great job! You met your sleep goal!");
        } else {
            sleepMessage.setText("You haven't met your sleep goal yet!");
        }
    }
}
