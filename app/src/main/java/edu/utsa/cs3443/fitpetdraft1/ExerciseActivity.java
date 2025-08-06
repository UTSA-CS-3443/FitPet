package edu.utsa.cs3443.fitpetdraft1;

import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;


public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Button exerciseButton = findViewById(R.id.exerciseButton);
        exerciseButton.setEnabled(false);
        exerciseButton.setAlpha(0.5f);

    }
}