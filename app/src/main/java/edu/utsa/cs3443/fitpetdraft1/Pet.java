package edu.utsa.cs3443.fitpetdraft1;

/**
 * Represents FitPet virtual pet
 * Pet has a name and mood that changes with user goals
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class Pet {
    private String name;
    private String mood;

    /**
     * Overloaded constructor
     * @param name the name of this pet
     */
    public Pet(String name) {
        this.name = name;
        this.mood = "sad";
    }

    /**
     * Updates the pets mood
     * @param goalsMet true if goals are met, false otherwise
     */
    public void updateMood(boolean goalsMet) {
        if (goalsMet) {
            this.mood = "HAPPY";
        } else {
            this.mood = "SAD";
        }
    }


    /**
     * Returns the current state of the object formatted
     * @return the current state of the object formatted
     */
    @Override
    public String toString() {

            return this.name + " is feeling " + this.mood + " today";
        }

}