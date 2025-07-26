package edu.utsa.cs3443.fitpetdraft1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<DayLog> allDays = new ArrayList<>();
    static DayLog currentDay = null;
    static UserGoals userGoals = null;
    static Pet pet;





    public static void main(String[] args) {


        setUserGoals();
        createMenu();
        System.exit(0);

    }



    public static void setUserGoals() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter pet's name: ");
        String petName = scan.nextLine();
        pet = new Pet(petName);

        System.out.print("Set daily water goal (oz): ");
        int water = Integer.parseInt(scan.nextLine());

        System.out.print("Set daily sleep goal (hours): ");
        int sleep = Integer.parseInt(scan.nextLine());

        System.out.print("Set daily exercise goal (calories burned): ");
        int exercise = Integer.parseInt(scan.nextLine());

        System.out.print("Set daily food calorie limit: ");
        int calories = Integer.parseInt(scan.nextLine());

        userGoals = new UserGoals(water, sleep, exercise, calories);

        System.out.println("Goals have been saved\n");
    }

    public static void createMenu() {
        int menuChoice = Utlities.menu();

        while (menuChoice != 6) {
            switch (menuChoice) {
                case 0:
                    startNewDay();
                    break;
                case 1:
                    addFood();
                    break;
                case 2:
                    addWater();
                    break;
                case 3:
                    addSleep();
                    break;
                case 4:
                    addExercise();
                    break;
                case 5:
                    viewHistory();
                default:
                    break;
            }
            menuChoice = Utlities.menu();
        }

        System.out.println("Terminating program...");
    }


    public static void viewHistory() {
        if (allDays.isEmpty()) {
            System.out.println("No history available.");
            return;
        }

        System.out.println("-------------------------- History of All Days ----------------------------");

        for (DayLog day : allDays) {
            System.out.println(day);

            if (userGoals == null) {
                System.out.println("User goals not set");
            } else {
                boolean goalsMet = day.goalsMet(userGoals);
                pet.updateMood(goalsMet);

                String petStatus = "";
                if (goalsMet) {
                    petStatus = "YES";
                }
                else{
                    petStatus = "NO";
                }
                System.out.println("All goals met: " + petStatus + " — " + pet.getMood());
            }

            System.out.println("--------------------------------\n");
        }
    }

    public static void viewCurrentDay() {
        if (currentDay == null) {
            System.out.println("No day has been started.");
        } else {
            System.out.println(currentDay);
            System.out.println(pet);
        }
    }

    public static void startNewDay() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter today's date (MM/DD/YY): ");
        String date = scan.nextLine();
        currentDay = new DayLog(date);
        allDays.add(currentDay);
        System.out.println("Started new day: " + date);
    }












    //-----------------FOOD/EXERCISE/SLEEP/WATER----------------------------------------------------------------------------------------
    public static void addFood() {


        if (currentDay == null) {
            System.out.println("Please start a new day first.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter food name: ");
        String name = scan.nextLine();
        System.out.print("Enter food fats: ");
        int fats = Integer.parseInt(scan.nextLine());
        System.out.print("Enter food carbs: ");
        int carbs = Integer.parseInt(scan.nextLine());
        System.out.print("Enter food protein: ");
        int protein = Integer.parseInt(scan.nextLine());
        currentDay.getFoodLog().add(new Food(name, fats, carbs, protein));
        System.out.println("Food added to " + currentDay.getDate());





    }

    public static void addExercise() {
        if (currentDay == null) {



            System.out.println("Please start a new day first.");
            return;


        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter exercise name: ");
        String name = scan.nextLine();
        System.out.print("Enter calories burned: ");
        int calories = Integer.parseInt(scan.nextLine());
        currentDay.getExerciseLog().add(new Exercise(name, calories));
        System.out.println("Exercise added to " + currentDay.getDate());
    }





    public static void addSleep() {
        if (currentDay == null) {
            System.out.println("Please start a new day first.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter hours slept: ");
        int hours = Integer.parseInt(scan.nextLine());

        currentDay.getSleepLog().add(new Sleep(hours));
        System.out.println("Sleep added to " + currentDay.getDate());
    }




    public static void addWater() {
        if (currentDay == null) {
            System.out.println("Please start a new day first.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter ounces of water: ");
        int ounces = Integer.parseInt(scan.nextLine());

        currentDay.getWaterLog().add(new Water(ounces));
        System.out.println("Water added to " + currentDay.getDate());
    }









}