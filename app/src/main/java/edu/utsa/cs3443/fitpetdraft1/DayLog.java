package edu.utsa.cs3443.fitpetdraft1;

import java.util.ArrayList;

/**
 * Holds a daily log of user input and user goals
 * Allows user to store food, exercise, water and sleep
 *
 *   @author Michael DeWitt
 *   @author Bella Rodriguez
 *   @author Sofia Galindo
 *   @author Jose Ramos-Rodriguez
 *
 */
public class DayLog {

    private String date;
    private ArrayList<Food> foodLog;
    private ArrayList<Water> waterLog;
    private ArrayList<Sleep> sleepLog;
    private ArrayList<Exercise> exerciseLog;

    /**
     * Creates a daily log for the day
     * @param date the date of the log
     */
    public DayLog(String date) {
        this.date = date;
        this.foodLog = new ArrayList<>();
        this.waterLog = new ArrayList<>();
        this.sleepLog = new ArrayList<>();
        this.exerciseLog = new ArrayList<>();
    }

    /**
     * Returns an array list of food items for the day
     * @return the array list of items
     */
    public ArrayList<Food> getFoodLog() {
        return foodLog;
    }

    /**
     * Returns an array list of water items for the day
     * @return an array list of water items
     */
    public ArrayList<Water> getWaterLog() {
        return waterLog;
    }

    /**
     * Returns an array list of sleep items for the day
     * @return an array list of sleep items
     */
    public ArrayList<Sleep> getSleepLog() {
        return sleepLog;
    }

    /**
     * An array list of exercise items for the day
     * @return an array list of sleep items
     */
    public ArrayList<Exercise> getExerciseLog() {
        return exerciseLog;
    }

    /**
     * Checks if all daily goals have been met
     * @param goals the user's daily goals
     * @return true if all goals have been met, false otherwise
     */
    public boolean goalsMet(UserGoals goals) {
        int totalWater = 0;
        for (Water w : waterLog) totalWater += w.getOunces();

        int totalSleep = 0;
        for (Sleep s : sleepLog) totalSleep += s.getHours();

        int totalExercise = 0;
        for (Exercise e : exerciseLog) totalExercise += e.getCaloriesBurned();

        int totalCalories = 0;
        for (Food f : foodLog) totalCalories += f.getCalories();

        int netCalories = Math.max(0, totalCalories - totalExercise);

        return totalWater >= goals.getWaterGoalOz()
                && totalSleep >= goals.getSleepGoalHours()
                && totalExercise >= goals.getExerciseGoalCalories()
                && netCalories <= goals.getFoodGoalCalories();
    }

    /**
     * Returns a formatted string of the daily log
     * @return a formatted string of the daily log
     */
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

