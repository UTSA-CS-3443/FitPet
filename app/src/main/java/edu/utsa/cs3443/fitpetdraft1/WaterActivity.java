package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

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


        addWaterButton.setOnClickListener(v -> {
            String waterStr = addWaterInput.getText().toString();
            int ounces = waterStr.isEmpty() ? 0 : Integer.parseInt(waterStr);

            Main.addWater(ounces);
            addWaterInput.setText("");


            int newTotal = Main.getTotalWaterToday();
            totalWaterText.setText(newTotal + " oz");
        });


        Button foodButton = findViewById(R.id.foodButton);
        Button sleepButton = findViewById(R.id.sleepButton);
        Button exerciseButton = findViewById(R.id.exerciseButton);

        foodButton.setOnClickListener(v -> startActivity(new Intent(this, FoodActivity.class)));
        sleepButton.setOnClickListener(v -> startActivity(new Intent(this, SleepActivity.class)));
        exerciseButton.setOnClickListener(v -> startActivity(new Intent(this, ExerciseActivity.class)));

    }
}