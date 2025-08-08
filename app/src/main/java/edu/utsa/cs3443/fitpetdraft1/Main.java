package edu.utsa.cs3443.fitpetdraft1;

import java.util.ArrayList;

public class Main {

    static ArrayList<DayLog> allDays = new ArrayList<>();
    static DayLog currentDay = null;
    static UserGoals userGoals = null;
    static Pet pet;

    public static void main(String[] args) {
        initializeDefaults();

        testApp();
    }

    static void initializeDefaults() {
        userGoals = new UserGoals(64, 8, 300, 2000);

        pet = new Pet("name");

        currentDay = new DayLog("07/26/2025");
        allDays.add(currentDay);

        System.out.println("FitPet initialized with default values");
        System.out.println("Goals: 64oz water, 8hrs sleep, 300 cal exercise, max 2000 cal food");
        System.out.println("Pet: " + pet);
    }

    private static void testApp() {
        System.out.println("\n--- Testing App Functionality ---");

        // Test adding some sample data
        addFood("Apple", 50, 0, 25, 0);
        addFood("Pasta", 200, 20, 30, 25);
        addExercise("Running", 200);
        addExercise("StairMaster", 150);
        addSleep(8);
        addWater(32);
        addWater(32);

        // Check status
        System.out.println("\n" + currentDay);
        System.out.println("Goals met: " + currentDay.goalsMet(userGoals));
        System.out.println(pet);
    }

    public static String getWelcomeMessage() {
        if (pet != null) {
            return "Welcome to FitPet! " + pet.toString();
        }
        return "Welcome to FitPet!";
    }

    public static String getDayStatusMessage() {
        if (currentDay == null) {
            return "No day started yet!";
        }

        StringBuilder message = new StringBuilder();
        message.append("Today's Progress:\n\n");

        if (userGoals != null) {
            message.append(String.format("Water: %d/%d oz (%.1f%%)\n",
                    getTotalWaterToday(), userGoals.getWaterGoalOz(), getWaterProgress()));
            message.append(String.format("Sleep: %d/%d hrs (%.1f%%)\n",
                    getTotalSleepToday(), userGoals.getSleepGoalHours(), getSleepProgress()));
            message.append(String.format("Exercise: %d/%d cal (%.1f%%)\n",
                    getTotalExerciseToday(), userGoals.getExerciseGoalCalories(), getExerciseProgress()));
            message.append(String.format("Food: %d/%d cal (%.1f%%)\n",
                    getTotalCaloriesToday(), userGoals.getFoodGoalCalories(), getFoodProgress()));
        }

        message.append("\n");
        if (pet != null) {
            message.append(pet.toString());
        }

        return message.toString();
    }

    public static boolean addFood(String name, int calories) {
        if (currentDay == null) return false;
        Food food = new Food(name, calories);
        currentDay.getFoodLog().add(food);
        updatePetMood();
        return true;
    }

    public static boolean addFood(String name, int calories, int fats, int carbs, int protein) {
        if (currentDay == null) return false;

        Food food = new Food(name, calories, fats, carbs, protein);
        currentDay.getFoodLog().add(food);
        updatePetMood();
        return true;
    }

    public static boolean addExercise(String name, int caloriesBurned) {
        if (currentDay == null) return false;

        Exercise exercise = new Exercise(name, caloriesBurned);
        currentDay.getExerciseLog().add(exercise);
        updatePetMood();
        return true;
    }

    public static boolean addSleep(int hours) {
        if (currentDay == null) return false;

        Sleep sleep = new Sleep(hours);
        currentDay.getSleepLog().add(sleep);
        updatePetMood();
        return true;
    }

    public static boolean addWater(int ounces) {
        if (currentDay == null) return false;

        Water water = new Water(ounces);
        currentDay.getWaterLog().add(water);
        updatePetMood();
        return true;
    }

    public static void initializeUserGoals(int waterOz, int sleepHours, int exerciseCalories, int foodCalories) {
        userGoals = new UserGoals(waterOz, sleepHours, exerciseCalories, foodCalories);
    }

    public static void initializePet(String name) {
        pet = new Pet(name);
    }

    public static void startNewDay(String date) {
        currentDay = new DayLog(date);
        allDays.add(currentDay);
        if (pet != null) {
            pet.updateMood(false);
        }
    }

    public static DayLog getCurrentDay() {
        return currentDay;
    }

    public static ArrayList<DayLog> getAllDays() {
        return allDays;
    }

    public static UserGoals getUserGoals() {
        return userGoals;
    }

    public static Pet getPet() {
        return pet;
    }

    public static boolean areGoalsMet() {
        if (currentDay == null || userGoals == null) return false;
        return currentDay.goalsMet(userGoals);
    }

    public static void updatePetMood() {
        if (pet != null && currentDay != null && userGoals != null) {
            boolean goalsMet = currentDay.goalsMet(userGoals);
            pet.updateMood(goalsMet);
        }
    }

    public static int getTotalWaterToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Water w : currentDay.getWaterLog()) {
            total += w.getOunces();
        }
        return total;
    }

    public static int getTotalSleepToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Sleep s : currentDay.getSleepLog()) {
            total += s.getHours();
        }
        return total;
    }

    public static int getTotalExerciseToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Exercise e : currentDay.getExerciseLog()) {
            total += e.getCaloriesBurned();
        }
        return total;
    }

    public static int getTotalCaloriesToday() {
        if (currentDay == null) return 0;
        int total = 0;
        for (Food f : currentDay.getFoodLog()) {
            total += f.getCalories();
        }
        return total;
    }

    public static double getWaterProgress() {
        if (userGoals == null) return 0.0;
        return Math.min(100.0, (getTotalWaterToday() / (double) userGoals.getWaterGoalOz()) * 100);
    }

    public static double getSleepProgress() {
        if (userGoals == null) return 0.0;
        return Math.min(100.0, (getTotalSleepToday() / (double) userGoals.getSleepGoalHours()) * 100);
    }

    public static double getExerciseProgress() {
        if (userGoals == null) return 0.0;
        return Math.min(100.0, (getTotalExerciseToday() / (double) userGoals.getExerciseGoalCalories()) * 100);
    }

    public static double getFoodProgress() {
        if (userGoals == null) return 0.0;
        int total = getTotalCaloriesToday();
        int goal = userGoals.getFoodGoalCalories();
        if (total <= goal) {
            return (total / (double) goal) * 100;
        } else {
            return 100.0;
        }
    }


}