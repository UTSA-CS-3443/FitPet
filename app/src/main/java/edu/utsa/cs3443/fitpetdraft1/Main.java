package edu.utsa.cs3443.fitpetdraft1;

import java.util.ArrayList;

/**
 *  The main class that connects the applications logic and state
 *  Stores user goals, pet data and the daily log
 *  Allows user to add new data, calculate totals and check if user daily goals are met
 *
 *   @author Michael DeWitt
 *   @author Bella Rodriguez
 *   @author Sofia Galindo
 *   @author Jose Ramos-Rodriguez
 *
 */
public class Main {

    static ArrayList<DayLog> allDays = new ArrayList<>();
    static DayLog currentDay = null;
    static UserGoals userGoals = null;
    static Pet pet;


    /**
     * Adds a food entry that only accepts name and calories
     * @param name the name of this food
     * @param calories the calories of this food
     * @return true if there is a day, therefore adding food. Otherwise if no current day, return false
     */
    public static boolean addFood(String name, int calories) {
        if (currentDay == null) return false;
        Food food = new Food(name, calories);
        currentDay.getFoodLog().add(food);
        updatePetMood();
        return true;
    }

    /**
     * Adds a food entry that accepts name, calories and macros
     * @param name the name of this food
     * @param calories the calories of this food
     * @param fats the fats of this food
     * @param carbs the carbs of this food
     * @param protein the protein of this food
     * @return true if there is a day, therefore adding food. Otherwise, if no current day, return false
     */
    public static boolean addFood(String name, int calories, int fats, int carbs, int protein) {
        if (currentDay == null) return false;
        Food food = new Food(name, calories, fats, carbs, protein);
        currentDay.getFoodLog().add(food);
        updatePetMood();
        return true;
    }

    /**
     * Adds an exercise entry
     * @param name the name of this exercise
     * @param caloriesBurned the calories burned of this exercise
     * @return true if there is a day, therefore adding exercise. Otherwise, if no current day, return false
     */
    public static boolean addExercise(String name, int caloriesBurned) {
        if (currentDay == null) return false;
        Exercise exercise = new Exercise(name, caloriesBurned);
        currentDay.getExerciseLog().add(exercise);
        updatePetMood();
        return true;
    }

    /**
     * Adds sleep entry
     * @param hours the hours of sleep
     * @return true if there is a day, therefore adding sleep. Otherwise, if no current day, return false
     */
    public static boolean addSleep(int hours) {
        if (currentDay == null) return false;
        Sleep sleep = new Sleep(hours);
        currentDay.getSleepLog().add(sleep);
        updatePetMood();
        return true;
    }

    /**
     * Adds water entry
     * @param ounces the ounces of water
     * @return true if there is a day, therefore adding water. Otherwise, if no current day, return false
     */
    public static boolean addWater(int ounces) {
        if (currentDay == null) return false;
        Water water = new Water(ounces);
        currentDay.getWaterLog().add(water);
        updatePetMood();
        return true;
    }

    /**
     * Initializes user's daily goals
     * @param waterOz the water ounces to be set
     * @param sleepHours the sleep hours to be set
     * @param exerciseCalories the exercise calories to be set
     * @param foodCalories the food calories to be set
     */
    public static void initializeUserGoals(int waterOz, int sleepHours, int exerciseCalories, int foodCalories) {
        userGoals = new UserGoals(waterOz, sleepHours, exerciseCalories, foodCalories);
    }

    /**
     * Initializes the pet
     * @param name the name of the pet
     */
    public static void initializePet(String name) {
        pet = new Pet(name);
    }

    /**
     * Starts a new day and resets day log
     * @param date the date of this day
     */
    public static void startNewDay(String date) {
        currentDay = new DayLog(date);
        allDays.add(currentDay);
        if (pet != null) {
            pet.updateMood(false);
        }
    }

    /**
     * Returns the current day
     * @return the current day
     */
    public static DayLog getCurrentDay() {
        return currentDay;
    }


    /**
     * Returns list of user goals
     * @return the list of user goals
     */
    public static UserGoals getUserGoals() {
        return userGoals;
    }

    /**
     * Returns the pet
     * @return the pet
     */
    public static Pet getPet() {
        return pet;
    }

    /**
     * Checks if daily goals are met
     * @return true if goals are met, false otherwise
     */
    public static boolean areGoalsMet() {
        if (currentDay == null || userGoals == null) return false;
        return currentDay.goalsMet(userGoals);
    }

    /**
     * Checks if daily goals are met considering exercise
     * @return true if goals are met, false otherwise
     */
    public static boolean areGoalsMetConsideringExercise() {
        if (userGoals == null) return false;
        boolean waterOk    = getTotalWaterToday()    >= userGoals.getWaterGoalOz();
        boolean sleepOk    = getTotalSleepToday()    >= userGoals.getSleepGoalHours();
        boolean exerciseOk = getTotalExerciseToday() >= userGoals.getExerciseGoalCalories();
        boolean foodOk     = getNetCaloriesToday()   <= userGoals.getFoodGoalCalories();
        return waterOk && sleepOk && exerciseOk && foodOk;
    }

    /**
     * Updates pet mood based on goals
     */
    public static void updatePetMood() {
        if (pet != null && currentDay != null && userGoals != null) {
            boolean goalsMet = areGoalsMetConsideringExercise();
            pet.updateMood(goalsMet);
        }
    }

    /**
     * Returns total water
     * @return total water
     */
    public static int getTotalWaterToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Water w : currentDay.getWaterLog()) {
            total += w.getOunces();
        }
        return total;
    }

    /**
     * Returns total sleep
     * @return total sleep
     */
    public static int getTotalSleepToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Sleep s : currentDay.getSleepLog()) {
            total += s.getHours();
        }
        return total;
    }

    /**
     * Returns total calories burned
     * @return calories burned
     */
    public static int getTotalExerciseToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Exercise e : currentDay.getExerciseLog()) {
            total += e.getCaloriesBurned();
        }
        return total;
    }

    /**
     * Returns total calories burned today
     * @return total calories burned today
     */
    public static int getTotalCaloriesToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Food f : currentDay.getFoodLog()) {
            total += f.getCalories();
        }
        return total;
    }

    /**
     * Returns net calories today
     * @return net calories today
     */
    public static int getNetCaloriesToday() {
        int net = getTotalCaloriesToday() - getTotalExerciseToday();
        return Math.max(0, net);
    }

    /**
     * Returns calories left today
     * @return calories left today
     */
    public static int getCaloriesLeftToday() {
        if (userGoals == null) return 0;
        int left = userGoals.getFoodGoalCalories() - getNetCaloriesToday();
        return Math.max(0, left);
    }


}
