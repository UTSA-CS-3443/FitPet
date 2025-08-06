package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;


public class ExerciseActivity extends AppCompatActivity {

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

        // update display
        int totalCalories = Main.getTotalExerciseToday();
        caloriesMessage.setText("You have burned " + totalCalories + " calories today.");

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // enter button
        enterButton.setOnClickListener(v -> {
            String type = typeInput.getText().toString();
            String duration = durationInput.getText().toString();
            String caloriesStr = caloriesInput.getText().toString();

            int calories = caloriesStr.isEmpty() ? 0 : Integer.parseInt(caloriesStr);

            Main.addExercise(type + " (" + duration + ")", calories);

            typeInput.setText("");
            durationInput.setText("");
            caloriesInput.setText("");

            // update display
            int newTotal = Main.getTotalExerciseToday();
            caloriesMessage.setText("You have burned " + newTotal + " calories today.");
        });

        // nav buttons
        Button foodButton = findViewById(R.id.foodButton);
        Button sleepButton = findViewById(R.id.sleepButton);
        Button waterButton = findViewById(R.id.waterButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        waterButton.setOnClickListener(v -> startActivity(new Intent(this, WaterActivity.class)));

    }
}