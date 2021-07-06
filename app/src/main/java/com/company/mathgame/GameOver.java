package com.company.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    Button restart;
    TextView finalScore;
    int scoreToDisplay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        restart = findViewById(R.id.restartButton2);
        finalScore = findViewById(R.id.finalScore);

        scoreToDisplay = getIntent().getIntExtra("finalScore", 0);
        finalScore.setText("Your final Score is: " + Integer.toString(scoreToDisplay));

        restart.setOnClickListener(v -> {
            Intent intentToRestart = new Intent (GameOver.this, MainActivity.class);
            startActivity(intentToRestart);
        });

    }
}
