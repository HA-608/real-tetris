package main;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A Leaderboard Panel.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class LeaderboardPanel creates a leaderboard recoding the maximum scores of all the users and displaying it.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */

public class LeaderboardPanel extends JPanel {
    private JFrame jFrame;
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;
    private User currentUser;

    public LeaderboardPanel(JFrame frame) {
        this(frame, null);
    }

    public LeaderboardPanel(JFrame frame, User user) {
        this.jFrame = frame;
        this.currentUser = user;
        setLayout(null);
        setBackground(new Color(30, 30, 30));
        setPreferredSize(new Dimension(1280, 720));

        JLabel titleLabel = new JLabel("LEADERBOARD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setBounds(450, 30, 400, 60);
        add(titleLabel);

        String[] columnNames = {"Rank", "Username", "Name", "High Score"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        leaderboardTable = new JTable(tableModel);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 18));
        leaderboardTable.setRowHeight(35);
        leaderboardTable.setBackground(new Color(50, 50, 50));
        leaderboardTable.setForeground(Color.WHITE);
        leaderboardTable.setGridColor(Color.GRAY);
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        leaderboardTable.getTableHeader().setBackground(new Color(70, 70, 70));
        leaderboardTable.getTableHeader().setForeground(Color.CYAN);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setBounds(200, 120, 880, 450);
        scrollPane.getViewport().setBackground(new Color(50, 50, 50));
        add(scrollPane);

        JButton backButton = new JButton(currentUser == null ? "BACK TO LOGIN" : "PLAY AGAIN");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setBounds(400, 600, 220, 50);
        backButton.setBackground(Color.GREEN);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        add(backButton);

        if (currentUser != null) {
            JButton mainMenuButton = new JButton("MAIN MENU");
            mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
            mainMenuButton.setBounds(660, 600, 220, 50);
            mainMenuButton.setBackground(Color.RED);
            mainMenuButton.setForeground(Color.WHITE);
            mainMenuButton.setFocusPainted(false);
            add(mainMenuButton);

            mainMenuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    goBackToLogin();
                }
            });
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser == null) {
                    goBackToLogin();
                } else {
                    playAgain();
                }
            }
        });

        loadLeaderboard();
    }

    private void loadLeaderboard() {
        tableModel.setRowCount(0);
        List<User> users = ScoreManager.getLeaderboard();

        int rank = 1;
        for (User user : users) {
            Object[] row = {
                    rank++,
                    user.getUsername(),
                    user.getFirstName() + " " + user.getLastName(),
                    user.getHighScore()
            };
            tableModel.addRow(row);
        }

        if (users.isEmpty()) {
            Object[] row = {"-", "No players yet", "-", "0"};
            tableModel.addRow(row);
        }
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

    private void goBackToLogin() {
        jFrame.getContentPane().removeAll();
        LoginPanel loginPanel = new LoginPanel(jFrame);
        jFrame.add(loginPanel);
        jFrame.revalidate();
        jFrame.repaint();
    }
}
