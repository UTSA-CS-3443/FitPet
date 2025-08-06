package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SleepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sleep);


        Button sleepButton = findViewById(R.id.sleepButton);
        sleepButton.setEnabled(false);
        sleepButton.setAlpha(0.5f);

    }
}