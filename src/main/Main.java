package main;

import javax.swing.JFrame;

/**
 * The Main Class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class Main.java launches the game and runs all other classes.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Start with login panel
        LoginPanel loginPanel = new LoginPanel(window);
        window.add(loginPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


    }
}