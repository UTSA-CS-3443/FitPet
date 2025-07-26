package edu.utsa.cs3443.fitpetdraft1;

public class Water implements GoalTracker {
    private int ounces;

    public Water(int ounces) {
        this.ounces = ounces;
    }

    public int getOunces() {
        return ounces;
    }

    @Override
    public boolean goalMet(UserGoals goals) {
        return ounces >= goals.getWaterGoalOz();
    }

    @Override
    public String toString() {
        return ounces + " oz";
    }
}