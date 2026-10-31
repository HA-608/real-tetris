package main;

import java.io.Serializable;

/**
 * The User class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class User.java uses io to record the log in information for all the users.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private int highScore;

    public User(String firstName, String lastName, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.highScore = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int newScore) {
        if (newScore > this.highScore) {
            this.highScore = newScore;
        }
    }

    @Override
    public String toString() {
        return username + "," + firstName + "," + lastName + "," + highScore;
    }
}
