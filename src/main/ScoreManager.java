package main;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The Score Manager class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class ScoreManager.java uses file io to record all the maximum scores of each user to be displayed in the leaderboard.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class ScoreManager {
    private static final String SCORES_FILE = "scores.txt";

    public static void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE, true))) {
            writer.write(user.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateHighScore(String username, int newScore) {
        List<String> lines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(username)) {
                    int currentScore = Integer.parseInt(parts[3]);
                    if (newScore > currentScore) {
                        parts[3] = String.valueOf(newScore);
                        line = String.join(",", parts);
                    }
                    updated = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<User> loadAllUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(SCORES_FILE);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    User user = new User(parts[1], parts[2], parts[0]);
                    user.setHighScore(Integer.parseInt(parts[3]));
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static List<User> getLeaderboard() {
        List<User> users = loadAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getHighScore(), u1.getHighScore()));
        return users;
    }

    public static boolean checkUsername(String username) {
        List<User> users = loadAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static User getNewUser(String username) {
        List<User> users = loadAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
