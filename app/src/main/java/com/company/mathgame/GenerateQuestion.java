package com.company.mathgame;

import androidx.appcompat.app.AppCompatActivity;

public class GenerateQuestion extends AppCompatActivity {
    private int answer;
    final GenerateRandomInt number = new GenerateRandomInt();

    public String generateQuestion() {
        String operation = MainActivity.operation;
        int first = number.generateRandomInt();
        int second = number.generateRandomInt();
        answer = operation.equals("addition")
                ? first + second
                : operation.equals("subtraction")
                ? first - second
                : operation.equals("multiplication")
                ? first * second
                : first / second;


        return operation.equals("addition")
                ? "" + first + " + " + second + "?"
                : operation.equals("subtraction")
                ? "" + first + " - " + second + "?"
                : operation.equals("multiplication")
                ? "" + first + " X " + second + "?"
                : "" + first + " / " + second + "?";
    }

    public boolean validate(String userInput) {
        return userInput.equals(Integer.toString(answer));
    }
}
