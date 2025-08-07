package edu.utsa.cs3443.fitpetdraft1;

public class Sleep {
    private int hours;

    public Sleep(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return hours + " hours";
    }
}