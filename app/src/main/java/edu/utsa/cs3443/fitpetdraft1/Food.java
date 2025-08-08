package edu.utsa.cs3443.fitpetdraft1;

public class Food {
    private String name;
    private int fats;
    private int carbs;
    private int protein;
    private int calories;

    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
        this.fats = 0;
        this.carbs = 0;
        this.protein = 0;
    }

    // constructor with macros
    public Food(String name, int calories, int fats, int carbs, int protein) {
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        if (fats == 0 && carbs == 0 && protein == 0) {
            return name + " | Calories: " + calories;
        } else {
            return name + " | Fats: " + fats + "g, Carbs: " + carbs + "g, Protein: " + protein + "g, Calories: " + calories;
        }
    }
}

