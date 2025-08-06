package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class WaterActivity extends AppCompatActivity {

    private TextView totalWaterText;
    private EditText addWaterInput;
    private Button addWaterButton;
    private int totalWater = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        totalWaterText = findViewById(R.id.totalWaterText);
        addWaterInput = findViewById(R.id.addWaterInput);
        addWaterButton = findViewById(R.id.addWaterButton);
        Button waterButton = findViewById(R.id.waterButton);
        waterButton.setEnabled(false);
        waterButton.setAlpha(0.5f);

        addWaterButton.setOnClickListener(v -> {
            String input = addWaterInput.getText().toString();
            if (!input.isEmpty()) {
                int addedWater = Integer.parseInt(input);
                totalWater += addedWater;
                totalWaterText.setText(totalWater + " oz");
                addWaterInput.setText("");
            }
        });

    }
}