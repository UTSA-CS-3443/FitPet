package edu.utsa.cs3443.fitpetdraft1;

/**
 * Represents a water entry for the day
 * Holds ounces of water
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class Water {
    private int ounces;

    /**
     * Overloaded constructor
     * @param ounces the ounces of water
     */
    public Water(int ounces) {
        this.ounces = ounces;
    }

    /**
     * Returns the ounces of water
     * @return the ounces of water
     */
    public int getOunces() {
        return ounces;
    }

    /**
     * Returns the current state of the object formatted
     * @return the current state of the object formatted
     */
    @Override
    public String toString() {
        return ounces + " oz";
    }
}