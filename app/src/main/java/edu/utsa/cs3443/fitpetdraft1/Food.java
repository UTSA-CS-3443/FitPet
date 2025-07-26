package edu.utsa.cs3443.fitpetdraft1;

public class Food implements GoalTracker {
    private String name;
    private int fats;
    private int carbs;
    private int protein;


    public Food(String name, int fats, int carbs, int protein) {
        this.name = name;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
    }

    public int getCalories() {
        return (fats * 9) + (carbs * 4) + (protein * 4);
    }

    @Override
    public boolean goalMet(UserGoals goals) {
        return getCalories() <= goals.getFoodGoalCalories();
    }

    @Override
    public String toString() {
        return name + " | Fats: " + fats + "g, Carbs: " + carbs + "g, Protein: " + protein + "g, Calories: " + getCalories();
    }
}

