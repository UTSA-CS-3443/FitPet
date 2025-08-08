package edu.utsa.cs3443.fitpetdraft1;

/**
 * Represents a sleep entry for the day
 * Holds hours of sleep
 *
 * @author Michael DeWitt
 * @author Bella Rodriguez
 * @author Sofia Galindo
 * @author Jose Ramos-Rodriguez
 *
 */
public class Sleep {
    private int hours;

    /**
     * Overloaded constructor
     * @param hours the hours of sleep
     */
    public Sleep(int hours) {
        this.hours = hours;
    }

    /**
     * Returns the hours of sleep
     * @return the hours of sleep
     */
    public int getHours() {
        return hours;
    }

    /**
     * Returns the current state of the object formatted
     * @return the current state of the object formatted
     */
    @Override
    public String toString() {
        return hours + " hours";
    }
}