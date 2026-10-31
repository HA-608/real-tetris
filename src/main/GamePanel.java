package main;


import javax.swing.*;
import java.awt.*;

/**
 * The Main Game Panel.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class GamePanel.java displays the panel in which the user plays tetris.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager playManager;
    User currentUser;
    boolean scoreUpdated = false;
    boolean gameOverShown = false;
    JFrame jFrame;

    public GamePanel(User user) {
        this(user, null);
    }

    public GamePanel(User user, JFrame frame) {
        this.currentUser = user;
        this.jFrame = frame;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        playManager = new PlayManager();
    }

    public void setjFrame(JFrame frame) {
        this.jFrame = frame;
    }

    public void resetGame() {
        scoreUpdated = false;
        gameOverShown = false;
        playManager.resetGame();
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        double lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (KeyHandler.pausePressed == false && !playManager.isGameOver()) {
            playManager.update();
        }

        if (playManager.isGameOver() && !scoreUpdated) {
            int finalScore = playManager.getScore();
            if (finalScore > currentUser.getHighScore()) {
                currentUser.setHighScore(finalScore);
                ScoreManager.updateHighScore(currentUser.getUsername(), finalScore);
            }
            scoreUpdated = true;

            Timer timer = new Timer(2000, e -> showGameOverScreen());
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void showGameOverScreen() {
        if (gameOverShown || jFrame == null) return;
        gameOverShown = true;
        gameThread = null;

        SwingUtilities.invokeLater(() -> {
            jFrame.getContentPane().removeAll();
            GameOverPanel gameOverPanel = new GameOverPanel(
                    jFrame,
                    currentUser,
                    playManager.getScore(),
                    playManager.getLinesCleared()
            );
            jFrame.add(gameOverPanel);
            jFrame.revalidate();
            jFrame.repaint();
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        playManager.draw(g1);

    }


}
