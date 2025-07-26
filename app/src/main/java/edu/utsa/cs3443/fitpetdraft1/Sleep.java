package edu.utsa.cs3443.fitpetdraft1;

public class Sleep implements GoalTracker {
    private int hours;

    public Sleep(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean goalMet(UserGoals goals) {
        return hours >= goals.getSleepGoalHours();
    }

    @Override
    public String toString() {
        return hours + " hours";
    }
}