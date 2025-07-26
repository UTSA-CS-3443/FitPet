package edu.utsa.cs3443.fitpetdraft1;

public class Exercise implements GoalTracker {
    private String name;
    private int caloriesBurned;

    public Exercise(String name, int caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    @Override
    public boolean goalMet(UserGoals goals) {
        return caloriesBurned >= goals.getExerciseGoalCalories();
    }

    @Override
    public String toString() {
        return name + " | Calories burned: " + caloriesBurned;
    }
}