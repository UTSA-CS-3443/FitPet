package edu.utsa.cs3443.fitpetdraft1;

/**
 * Represents an exercise entry for the day
 * Holds exercise name and calories burned
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class Exercise {
    private String name;
    private int caloriesBurned;

    /**
     * Overloaded constructor
     * @param name the name of this exercise
     * @param caloriesBurned the calories burned of this exercise
     */
    public Exercise(String name, int caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    /**
     * Returns the calories burned of this exercise
     * @return the calories burned of this exercise
     */
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    /**
     * Returns the current state of the object formatted
     * @return the current state of the object formatted
     */
    @Override
    public String toString() {
        return name + " | Calories burned: " + caloriesBurned;
    }
}