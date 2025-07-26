package edu.utsa.cs3443.fitpetdraft1;
public class UserGoals {
    private int waterGoalOz;
    private int sleepGoalHours;
    private int exerciseGoalCalories;
    private int foodGoalCalories;

    public UserGoals(int waterGoalOz, int sleepGoalHours, int exerciseGoalCalories, int foodGoalCalories) {
        this.waterGoalOz = waterGoalOz;
        this.sleepGoalHours = sleepGoalHours;
        this.exerciseGoalCalories = exerciseGoalCalories;
        this.foodGoalCalories = foodGoalCalories;
    }

    public int getWaterGoalOz(){
        return waterGoalOz;

    }

    public int getSleepGoalHours(){

        return sleepGoalHours;
    }

    public int getExerciseGoalCalories(){

        return exerciseGoalCalories;

    }
    public int getFoodGoalCalories(){

        return foodGoalCalories;

    }
}