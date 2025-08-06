package edu.utsa.cs3443.fitpetdraft1;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DayManager {

    private static final String PREFS_NAME = "FitPetPrefs";
    private static final String LAST_DATE_KEY = "lastDate";
    private static final String SUMMARY_FILE = "daily_summaries.txt";

    public static boolean checkAndHandleNewDay(Context context) throws IOException {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String lastDate = prefs.getString(LAST_DATE_KEY, "");

        if (!today.equals(lastDate)) {
            if (!lastDate.isEmpty()) {
                saveDailySummaryToFile(context, lastDate);
                handleDayTransition();
            }

            Main.startNewDay(today);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(LAST_DATE_KEY, today);
            editor.apply();
            return true;
        }

        if (Main.getCurrentDay() == null) {
            Main.startNewDay(today);
        }
        return false;
    }

    private static void saveDailySummaryToFile(Context context, String date) throws IOException {
        String summary = generateDailySummaryForFile(date);

        FileOutputStream fos = context.openFileOutput(SUMMARY_FILE, Context.MODE_APPEND);
        OutputStreamWriter writer = new OutputStreamWriter(fos);
        writer.write(summary);
        writer.write("\n" + "=" + "=".repeat(50) + "\n\n");
        writer.close();
        fos.close();
    }

    private static String generateDailySummaryForFile(String date) {
        StringBuilder summary = new StringBuilder();

        summary.append("DAILY SUMMARY - ").append(date).append("\n");
        summary.append("Generated: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date())).append("\n\n");

        if (Main.getCurrentDay() != null && Main.getUserGoals() != null) {
            int waterTotal = Main.getTotalWaterToday();
            int waterGoal = Main.getUserGoals().getWaterGoalOz();
            int sleepTotal = Main.getTotalSleepToday();
            int sleepGoal = Main.getUserGoals().getSleepGoalHours();
            int exerciseTotal = Main.getTotalExerciseToday();
            int exerciseGoal = Main.getUserGoals().getExerciseGoalCalories();
            int foodTotal = Main.getTotalCaloriesToday();
            int foodGoal = Main.getUserGoals().getFoodGoalCalories();

            summary.append("GOALS PROGRESS:\n");
            summary.append("Water: ").append(waterTotal).append("/").append(waterGoal).append(" oz");
            summary.append(waterTotal >= waterGoal ? " [ACHIEVED]" : " [NOT MET]").append("\n");

            summary.append("Sleep: ").append(sleepTotal).append("/").append(sleepGoal).append(" hours");
            summary.append(sleepTotal >= sleepGoal ? " [ACHIEVED]" : " [NOT MET]").append("\n");

            summary.append("Exercise: ").append(exerciseTotal).append("/").append(exerciseGoal).append(" calories");
            summary.append(exerciseTotal >= exerciseGoal ? " [ACHIEVED]" : " [NOT MET]").append("\n");

            summary.append("Food: ").append(foodTotal).append("/").append(foodGoal).append(" calories");
            summary.append(foodTotal <= foodGoal ? " [ACHIEVED]" : " [EXCEEDED]").append("\n\n");

            boolean allGoalsMet = Main.areGoalsMet();
            summary.append("OVERALL STATUS: ").append(allGoalsMet ? "ALL GOALS ACHIEVED!" : "GOALS NOT COMPLETED").append("\n");

            if (Main.getPet() != null) {
                summary.append("PET STATUS: ").append(Main.getPet().toString()).append("\n");
            }
        } else {
            summary.append("No data available for this day.\n");
        }

        return summary.toString();
    }

    private static void handleDayTransition() {
        if (Main.getUserGoals() != null && Main.getCurrentDay() != null) {
            boolean goalsMet = Main.areGoalsMet();
            Main.updatePetMood();
        }
    }

    public static void saveCurrentDayToFile(Context context) throws IOException {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        saveDailySummaryToFile(context, today);
    }
}
