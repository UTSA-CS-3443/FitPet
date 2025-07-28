package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        Button foodButton = findViewById(R.id.foodButton);
        foodButton.setEnabled(false);
        foodButton.setAlpha(0.5f);

        EditText foodNameInput = findViewById(R.id.foodNameInput);
        EditText fatsInput = findViewById(R.id.fatsInput);
        EditText carbsInput = findViewById(R.id.carbsInput);
        EditText proteinInput = findViewById(R.id.proteinInput);
        Button saveFoodButton = findViewById(R.id.saveFoodButton);

        saveFoodButton.setOnClickListener(v -> {
            String name = foodNameInput.getText().toString();
            String fatsStr = fatsInput.getText().toString();
            String carbsStr = carbsInput.getText().toString();
            String proteinStr = proteinInput.getText().toString();

            int fats = fatsStr.isEmpty() ? 0 : Integer.parseInt(fatsStr);
            int carbs = carbsStr.isEmpty() ? 0 : Integer.parseInt(carbsStr);
            int protein = proteinStr.isEmpty() ? 0 : Integer.parseInt(proteinStr);

            System.out.println("Food added: " + name + " | Fats: " + fats + " | Carbs: " + carbs + " | Protein: " + protein);

        });

    }
}