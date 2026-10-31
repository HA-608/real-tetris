package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The end game panel.
 *
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 *Java class GameOverPanel.java will display the page after the user loses.
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */



public class GameOverPanel extends JPanel {
    private JFrame jFrame;
    private User currentUser;
    private int finalScore;
    private int linesCleared;

    public GameOverPanel(JFrame frame, User user, int score, int lines) {
        this.jFrame = frame;
        this.currentUser = user;
        this.finalScore = score;
        this.linesCleared = lines;

        setLayout(null);
        setBackground(new Color(30, 30, 30));
        setPreferredSize(new Dimension(1280, 720));

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 70));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setBounds(400, 100, 500, 80);
        add(gameOverLabel);

        JLabel playerLabel = new JLabel("Player: " + user.getUsername());
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setBounds(480, 200, 400, 40);
        add(playerLabel);

        JLabel scoreLabel = new JLabel("Final Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBounds(450, 270, 400, 50);
        add(scoreLabel);

        JLabel linesLabel = new JLabel("Lines Cleared: " + lines);
        linesLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        linesLabel.setForeground(Color.CYAN);
        linesLabel.setBounds(480, 340, 400, 40);
        add(linesLabel);

        JLabel highScoreLabel = new JLabel("Your High Score: " + user.getHighScore());
        highScoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        highScoreLabel.setForeground(Color.GREEN);
        highScoreLabel.setBounds(470, 400, 400, 35);
        add(highScoreLabel);

        JButton playAgainButton = new JButton("PLAY AGAIN");
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 25));
        playAgainButton.setBounds(400, 480, 220, 60);
        playAgainButton.setBackground(Color.GREEN);
        playAgainButton.setForeground(Color.BLACK);
        playAgainButton.setFocusPainted(false);
        add(playAgainButton);

        JButton leaderboardButton = new JButton("LEADERBOARD");
        leaderboardButton.setFont(new Font("Arial", Font.BOLD, 22));
        leaderboardButton.setBounds(660, 480, 250, 60);
        leaderboardButton.setBackground(Color.ORANGE);
        leaderboardButton.setForeground(Color.BLACK);
        leaderboardButton.setFocusPainted(false);
        add(leaderboardButton);

        JButton mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuButton.setBounds(520, 560, 240, 50);
        mainMenuButton.setBackground(Color.LIGHT_GRAY);
        mainMenuButton.setForeground(Color.BLACK);
        mainMenuButton.setFocusPainted(false);
        add(mainMenuButton);

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgain();
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLeaderboard();
            }
        });

        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnMainMenu();
            }
        });
    }

    private void playAgain() {
        jFrame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(currentUser, jFrame);
        gamePanel.resetGame();
        jFrame.add(gamePanel);
        jFrame.revalidate();
        jFrame.repaint();
        gamePanel.requestFocusInWindow();
        gamePanel.launchGame();
    }

    private void displayLeaderboard() {
        jFrame.getContentPane().removeAll();
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel(jFrame, currentUser);
        jFrame.add(leaderboardPanel);
        jFrame.revalidate();
        jFrame.repaint();
    }

    private void returnMainMenu() {
        jFrame.getContentPane().removeAll();
        LoginPanel loginPanel = new LoginPanel(jFrame);
        jFrame.add(loginPanel);
        jFrame.revalidate();
        jFrame.repaint();
    }
}
