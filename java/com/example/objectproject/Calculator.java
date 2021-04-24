package com.example.objectproject;

public class Calculator {
    private double bmi = 0;

    public double bmiCalc(int weight, int height) {

        bmi = ((double) weight / (((double) height/100) * ((double) height/100)));
        return bmi;
    }

    public int calorieGoalCalc(String gender, int weight, int height, int age, String activityLevel) {
        int calorieGoal = 0;
        double bmr = 0;

        if (gender.equals("Male")) {
            bmr = 66.47 + (13.75*weight) + (5.003*height) - (6.755*age);
        }
        else if (gender.equals("Female")) {
            bmr = 655.1 + (9.563*weight) + (1.85*height) - (4.676*age);
        }

        double activity = 0;

        switch (activityLevel) {
            case "Sedentary":
                activity = 1.2;
                break;
            case "Light":
                activity = 1.375;
                break;
            case "Moderate":
                activity = 1.55;
                break;
            case "Active":
                activity = 1.725;
                break;
            case "Very Active":
                activity = 1.9;
                break;
        }

        calorieGoal = (int) (bmr * activity);

        return calorieGoal;
    }
}
