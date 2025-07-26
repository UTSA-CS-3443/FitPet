package edu.utsa.cs3443.fitpetdraft1;

import java.util.ArrayList;

public class DayLog {

    private String date;
    private ArrayList<Food> foodLog;
    private ArrayList<Water> waterLog;
    private ArrayList<Sleep> sleepLog;
    private ArrayList<Exercise> exerciseLog;

    public DayLog(String date) {
        this.date = date;
        this.foodLog = new ArrayList<>();
        this.waterLog = new ArrayList<>();
        this.sleepLog = new ArrayList<>();
        this.exerciseLog = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Food> getFoodLog() {
        return foodLog;
    }

    public ArrayList<Water> getWaterLog() {
        return waterLog;
    }

    public ArrayList<Sleep> getSleepLog() {
        return sleepLog;
    }

    public ArrayList<Exercise> getExerciseLog() {
        return exerciseLog;
    }

    public boolean goalsMet(UserGoals goals) {
        int totalWater = 0;
        for (Water w : waterLog) {
            totalWater += w.getOunces();
        }

        int totalSleep = 0;
        for (Sleep s : sleepLog) {
            totalSleep += s.getHours();
        }

        int totalExercise = 0;
        for (Exercise e : exerciseLog) {
            totalExercise += e.getCaloriesBurned();
        }

        int totalCalories = 0;
        for (Food f : foodLog) {
            totalCalories += f.getCalories();
        }

        return totalWater >= goals.getWaterGoalOz()
                && totalSleep >= goals.getSleepGoalHours()
                && totalExercise >= goals.getExerciseGoalCalories()
                && totalCalories <= goals.getFoodGoalCalories();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Daily Log for ").append(date).append(":\n\n");

        sb.append(" Food:\n");
        for (Food f : foodLog) {
            sb.append("  - ").append(f).append("\n");
        }

        sb.append("\n Exercise:\n");
        for (Exercise e : exerciseLog) {
            sb.append("  - ").append(e).append("\n");
        }

        sb.append("\n Sleep:\n");
        for (Sleep s : sleepLog) {
            sb.append("  - ").append(s.getHours()).append(" hours\n");
        }

        sb.append("\n Water:\n");
        for (Water w : waterLog) {
            sb.append("  - ").append(w.getOunces()).append(" oz\n");
        }

        return sb.toString();
    }
}
