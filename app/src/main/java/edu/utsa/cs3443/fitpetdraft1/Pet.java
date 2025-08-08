package edu.utsa.cs3443.fitpetdraft1;

public class Pet {
    private String name;
    private String mood;

    public Pet(String name) {
        this.name = name;
        this.mood = "sad";
    }

    public void updateMood(boolean goalsMet) {
        if (goalsMet) {
            mood = "happy";
        } else {
            mood = "sad";
        }
    }

    public String getMood() {
        return mood;
    }

    @Override
    public String toString() {
        return name + " is feeling " + mood + " today.";
    }
}