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
            this.mood = "HAPPY";
        } else {
            this.mood = "SAD";
        }
    }

    public String getMood() {
        return mood;
    }

    @Override
    public String toString() {

            return this.name + " is feeling " + this.mood + " today";
        }

}