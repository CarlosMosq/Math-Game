package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Switch livesSwitch;
    Switch timeSwitch;
    RadioGroup group;
    RadioButton addition;
    RadioButton subtraction;
    RadioButton multiplication;
    RadioButton division;
    Button start;
    SharedPreferences sharing;
    public int lives;
    public int time;
    public static String operation;
    public boolean isLifeChecked;
    public boolean isTimeChecked;
    public boolean isAddChecked;
    public boolean isSubChecked;
    public boolean isMulChecked;
    public boolean isDivChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        livesSwitch = findViewById(R.id.livesSwitch);
        timeSwitch = findViewById(R.id.timeSwitch);
        group = findViewById(R.id.radioGroup);
        addition = findViewById(R.id.addition);
        subtraction = findViewById(R.id.subtraction);
        multiplication = findViewById(R.id.multiplication);
        division = findViewById(R.id.division);
        start = findViewById(R.id.startButton);

        start.setOnClickListener(v -> {
            if (livesSwitch.isChecked()) lives = 3;
            if (!livesSwitch.isChecked()) lives = 5;
            if (timeSwitch.isChecked()) time = 45;
            if (!timeSwitch.isChecked()) time = 60;
            if (addition.isChecked()) operation = "addition";
            if (subtraction.isChecked()) operation = "subtraction";
            if (multiplication.isChecked()) operation = "multiplication";
            if (division.isChecked()) operation = "division";

            Intent intent = new Intent (MainActivity.this, GamePlay.class);
            intent.putExtra("lifeValue", lives);
            intent.putExtra("timeValue", time);
            intent.putExtra("operation", operation);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        retrieveData();
        System.out.println("onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    public void saveData () {
        sharing = getSharedPreferences("saveData", Context.MODE_PRIVATE);
        isLifeChecked = livesSwitch.isChecked();
        isTimeChecked = timeSwitch.isChecked();
        isAddChecked = addition.isChecked();
        isSubChecked = subtraction.isChecked();
        isMulChecked = multiplication.isChecked();
        isDivChecked = division.isChecked();


        SharedPreferences.Editor editor = sharing.edit();
        editor.putBoolean("lifeSwitch", isLifeChecked);
        editor.putBoolean("timeSwitch", isTimeChecked);
        editor.putBoolean("addition", isAddChecked);
        editor.putBoolean("subtraction", isSubChecked);
        editor.putBoolean("multiplication", isMulChecked);
        editor.putBoolean("division", isDivChecked);

        Toast.makeText(getApplicationContext(), "Your data is saved", Toast.LENGTH_LONG).show();
        editor.apply();
    }

    public void retrieveData() {
        sharing = getSharedPreferences("saveData", Context.MODE_PRIVATE);

        isLifeChecked = sharing.getBoolean("lifeSwitch", false);
        isTimeChecked = sharing.getBoolean("timeSwitch", false);
        isAddChecked = sharing.getBoolean("addition", false);
        isSubChecked = sharing.getBoolean("subtraction", false);
        isMulChecked = sharing.getBoolean("multiplication", false);
        isDivChecked = sharing.getBoolean("division", false);

        if (isLifeChecked) livesSwitch.setChecked(true);
        if (isTimeChecked) timeSwitch.setChecked(true);
        if (isAddChecked) addition.setChecked(true);
        if (isSubChecked) subtraction.setChecked(true);
        if (isMulChecked) multiplication.setChecked(true);
        if (isDivChecked) division.setChecked(true);
    }

}