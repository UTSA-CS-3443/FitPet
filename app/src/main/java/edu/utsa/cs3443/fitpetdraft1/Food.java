package edu.utsa.cs3443.fitpetdraft1;

/**
 * Represents a food entry for the day
 * Holds food name, fats, carbs, protein and calories
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class Food {
    private String name;
    private int fats;
    private int carbs;
    private int protein;
    private int calories;

    /**
     * Overloaded constructor
     * @param name the name of this food
     * @param calories the calories of this food
     */
    public Food(String name, int calories) {
        this.name = name;
        this.calories = calories;
        this.fats = 0;
        this.carbs = 0;
        this.protein = 0;
    }

    /**
     * Overloaded constructor with additional parameters
     * @param name the name of this food
     * @param calories the calories of this food
     * @param fats the fats of this food
     * @param carbs the carbs of this food
     * @param protein the protein of this food
     */
    public Food(String name, int calories, int fats, int carbs, int protein) {
        this.name = name;
        this.calories = calories;
        this.fats = fats;
        this.carbs = carbs;
        this.protein = protein;
    }

    /**
     * Returns food calories
     * @return food calories
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Returns the current state of the object formatted
     * @return the current state of the object
     */
    @Override
    public String toString() {
        if (fats == 0 && carbs == 0 && protein == 0) {
            return name + " | Calories: " + calories;
        } else {
            return name + " | Fats: " + fats + "g, Carbs: " + carbs + "g, Protein: " + protein + "g, Calories: " + calories;
        }
    }
}

