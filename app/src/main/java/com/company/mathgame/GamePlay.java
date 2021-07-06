package com.company.mathgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;

public class GamePlay extends AppCompatActivity {
    EditText score;
    EditText life;
    EditText time;
    TextView answer;
    EditText answerValue;
    Button ok;
    Button skip;
    Button restart;
    int scoreValue = 0;
    int lifeValue;
    int timeValue;
    String answerText;
    SharedPreferences sharing;
    String operation;
    CountDownTimer timer;
    private long timeLeftInMil;
    GenerateQuestion question;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        score = findViewById(R.id.scoreNumber);
        life = findViewById(R.id.lifeNumber);
        time = findViewById(R.id.timeNumber);
        answer = findViewById(R.id.questionField);
        ok = findViewById(R.id.okButton);
        skip = findViewById(R.id.skipButton);
        answerValue = findViewById(R.id.answerValue);
        restart = findViewById(R.id.restartButton);

        Intent intent = getIntent();
        lifeValue = intent.getIntExtra("lifeValue", 3);
        timeValue = intent.getIntExtra("timeValue", 60);
        operation = MainActivity.operation;

        timeLeftInMil = timeValue * 1000;
        countDown();

        score.setText("" + scoreValue);
        life.setText("" + lifeValue);
        time.setText("" + timeValue);

        question = new GenerateQuestion();
        answer.setText(question.generateQuestion());


        ok.setOnClickListener(v -> {
            if (question.validate(answerValue.getText().toString()) && timeValue > 0 && lifeValue > 0) {
                Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                scoreValue++;
                score.setText("" + scoreValue);
                answer.setText(question.generateQuestion());
                answerValue.setText("");
                restartCountdown();
            }

            else if (!question.validate(answerValue.getText().toString()) && lifeValue > 0) {
                Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                lifeValue--;
                life.setText("" + lifeValue);
                answer.setText(question.generateQuestion());
                answerValue.setText("");
                restartCountdown();
            }
            else if (lifeValue <= 0 || timeValue <= 0) {
                gameOver();
            }
        });

        skip.setOnClickListener(v -> {
            restartCountdown();
            answer.setText(question.generateQuestion());
        });

        restart.setOnClickListener(v -> {
            Intent intentToRestart = new Intent (GamePlay.this, MainActivity.class);
            startActivity(intentToRestart);
        });


    }


    public void gameOver() {
        Intent intentOver = new Intent(GamePlay.this, GameOver.class);
        intentOver.putExtra("finalScore", scoreValue);
        startActivity(intentOver);
        lifeValue = 5;
        timeValue = 60;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        retrieveData();
        System.out.println("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        System.out.println("onPause");
    }

    public void countDown() {
        timer = new CountDownTimer(timeLeftInMil, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int newTime = timeValue;
                timeLeftInMil = millisUntilFinished;
                newTime = (int) (timeLeftInMil / 1000);
                time.setText("" + newTime);
            }

            @Override
            public void onFinish() {
                if (lifeValue == 0) gameOver();
                if (lifeValue > 0) lifeValue--;
                life.setText("" + lifeValue);
                answer.setText(question.generateQuestion());
                answerValue.setText("");
                timeLeftInMil = timeValue * 1000;
                this.start();
            }
        }.start();
    }

    public void restartCountdown() {
        timer.cancel();
        timeLeftInMil = timeValue * 1000;
        countDown();
    }

    public void saveData () {
        sharing = getSharedPreferences("saveData", Context.MODE_PRIVATE);
        answerText = answerValue.getText().toString();

        SharedPreferences.Editor editor = sharing.edit();
        editor.putInt("scoreValue", scoreValue);
        editor.putInt("lifeValue", lifeValue);
        editor.putInt("timeValue", timeValue);
        editor.putString("answerValue", answerText);
        Toast.makeText(getApplicationContext(), "Your data is saved", Toast.LENGTH_LONG).show();
        editor.apply();
    }

    public void retrieveData() {
        sharing = getSharedPreferences("saveData", Context.MODE_PRIVATE);

        scoreValue = sharing.getInt("scoreValue", 0);
        lifeValue = sharing.getInt("lifeValue", 3);
        timeValue = sharing.getInt("timeValue", 60);
        answerText = sharing.getString("answerValue", null);

        score.setText("" + scoreValue);
        life.setText("" + lifeValue);
        time.setText("" + timeValue);
        answerValue.setText(answerText);
    }




    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }
}
