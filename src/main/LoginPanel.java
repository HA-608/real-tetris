package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Login Panel.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class LoginPanel.java deals with the page displaying first name, last name and username, prompting the user to respond.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class LoginPanel extends JPanel {
    private JTextField fNField; // first name field
    private JTextField lNField; // last name field
    private JTextField usernameField;
    private JButton submitButton;
    private JButton leaderboardButton;
    private User currentUser;
    private JFrame jFrame;

    public LoginPanel(JFrame frame) {
        this.jFrame = frame;
        setLayout(null);
        setBackground(new Color(30, 30, 30));
        setPreferredSize(new Dimension(1280, 720));

        JLabel titleLabel = new JLabel("TETRIS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setBounds(500, 100, 300, 80);
        add(titleLabel);

        JLabel fNLabel = new JLabel("First Name:");
        fNLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fNLabel.setForeground(Color.WHITE);
        fNLabel.setBounds(400, 250, 150, 30);
        add(fNLabel);

        fNField = new JTextField();
        fNField.setFont(new Font("Arial", Font.PLAIN, 18));
        fNField.setBounds(550, 250, 300, 35);
        add(fNField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setBounds(400, 310, 150, 30);
        add(lastNameLabel);

        lNField = new JTextField();
        lNField.setFont(new Font("Arial", Font.PLAIN, 18));
        lNField.setBounds(550, 310, 300, 35);
        add(lNField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(400, 370, 150, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setBounds(550, 370, 300, 35);
        add(usernameField);

        submitButton = new JButton("START GAME");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setBounds(500, 450, 250, 50);
        submitButton.setBackground(Color.GREEN);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);
        add(submitButton);

        leaderboardButton = new JButton("VIEW LEADERBOARD");
        leaderboardButton.setFont(new Font("Arial", Font.BOLD, 18));
        leaderboardButton.setBounds(480, 520, 300, 45);
        leaderboardButton.setBackground(Color.ORANGE);
        leaderboardButton.setForeground(Color.BLACK);
        leaderboardButton.setFocusPainted(false);
        add(leaderboardButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFunction();
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLeaderboard();
            }
        });
    }

    private void submitFunction() {
        String firstName = fNField.getText().trim();
        String lastName = lNField.getText().trim();
        String username = usernameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ScoreManager.checkUsername(username)) {
            currentUser = ScoreManager.getNewUser(username);
            JOptionPane.showMessageDialog(this, "Welcome back, " + firstName + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        } else {
            currentUser = new User(firstName, lastName, username);
            ScoreManager.saveUserToFile(currentUser);
            JOptionPane.showMessageDialog(this, "Account created! Welcome, " + firstName + "!", "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        }

        launchGame();
    }

    private void launchGame() {
        jFrame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(currentUser, jFrame);
        jFrame.add(gamePanel);
        jFrame.revalidate();
        jFrame.repaint();
        gamePanel.requestFocusInWindow();
        gamePanel.launchGame();
    }

    private void displayLeaderboard() {
        jFrame.getContentPane().removeAll();
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel(jFrame);
        jFrame.add(leaderboardPanel);
        jFrame.revalidate();
        jFrame.repaint();
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
