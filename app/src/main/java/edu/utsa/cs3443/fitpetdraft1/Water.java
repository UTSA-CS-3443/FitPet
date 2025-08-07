package edu.utsa.cs3443.fitpetdraft1;

public class Water {
    private int ounces;

    public Water(int ounces) {
        this.ounces = ounces;
    }

    public int getOunces() {
        return ounces;
    }

    @Override
    public String toString() {
        return ounces + " oz";
    }
}