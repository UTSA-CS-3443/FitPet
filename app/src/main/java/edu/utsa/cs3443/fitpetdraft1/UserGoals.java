package edu.utsa.cs3443.fitpetdraft1;

/**
 * The daily user defined health goals
 * Goals include water intake, sleep duration, calories burned and food calorie intake.
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class UserGoals {
    private int waterGoalOz;
    private int sleepGoalHours;
    private int exerciseGoalCalories;
    private int foodGoalCalories;

    /**
     * Creates a user goal object
     * @param waterGoalOz the water goal of this object
     * @param sleepGoalHours the sleep goal of this object
     * @param exerciseGoalCalories the exercise goal of this object
     * @param foodGoalCalories the food calories of this object
     */
    public UserGoals(int waterGoalOz, int sleepGoalHours, int exerciseGoalCalories, int foodGoalCalories) {
        this.waterGoalOz = waterGoalOz;
        this.sleepGoalHours = sleepGoalHours;
        this.exerciseGoalCalories = exerciseGoalCalories;
        this.foodGoalCalories = foodGoalCalories;
    }

    /**
     * Returns daily water intake goal
     * @return the water intake goal
     */
    public int getWaterGoalOz(){
        return waterGoalOz;

    }

    /**
     * Returns the daily sleep goal
     * @return the daily sleep goal
     */
    public int getSleepGoalHours(){

        return sleepGoalHours;
    }

    /**
     * Returns the exercise calorie goal
     * @return the exercise calorie goal
     */
    public int getExerciseGoalCalories(){

        return exerciseGoalCalories;

    }

    /**
     * Returns the food calorie goal
     * @return the food calorie goal
     */
    public int getFoodGoalCalories(){

        return foodGoalCalories;

    }
}