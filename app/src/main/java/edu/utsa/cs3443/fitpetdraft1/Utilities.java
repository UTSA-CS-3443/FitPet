package edu.utsa.cs3443.fitpetdraft1;


import javax.swing.JOptionPane;
import javax.swing.ImageIcon;


public class Utlities {

    public static int menu(){
        Object [] options = {"Enter Date", "Add Food", "Add water", "Add Sleep", "Add Exercise", "Show Entire History", "Exit"};


        ImageIcon icon = new ImageIcon("pet1.png");
        int n = JOptionPane.showOptionDialog(null,
                "Welcome to the Pet Menu! Please select an option below.",
                "Pet Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                icon,
                options,
                options[0]);

        return n;
    }


}
