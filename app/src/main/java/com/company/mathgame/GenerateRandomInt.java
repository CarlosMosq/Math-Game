package com.company.mathgame;

public class GenerateRandomInt {
    public int generateRandomInt() {
        int max = 100;
        int min = 1;
        int range = max - min + 1;

        return (int) (Math.random() * range) + 1;
    }
}
