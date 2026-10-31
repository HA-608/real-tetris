package main;

import tetromino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Play Manager class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class PlayerManager.java deals with creating the gui for the game and also dealing with some mechanics pertaining to tetris.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class PlayManager {
    //Main Play Area

    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXT_MINO_START_X;
    final int NEXT_MINO_START_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    private int score = 0;
    private int linesCleared = 0;

    public static int dropInterval = 60; // mino drops in every 60 frames
    boolean gameOver = false;

    public PlayManager() {

        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        // Set the starting Mino
        currentMino = randomMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        NEXT_MINO_START_X = right_x + 175;
        NEXT_MINO_START_Y = top_y + 500;
        nextMino = randomMino();
        nextMino.setXY(NEXT_MINO_START_X, NEXT_MINO_START_Y);
    }

    private Mino randomMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0:
                mino = new Mino_Z2();
                break;
            case 1:
                mino = new Mino_Z2();
                break;
            case 2:
                mino = new Mino_L1();
                break;
            case 3:
                mino = new Mino_L2();
                break;
            case 4:
                mino = new Mino_Square();
                break;
            case 5:
                mino = new Mino_T();
                break;
            case 6:
                mino = new Mino_Bar();
                break;
        }
        return mino;
    }

    public void update() {

        if (!gameOver && currentMino.active == false) {

            // if the mino is not active, put it in static blocks
            staticBlocks.add(currentMino.block[0]);
            staticBlocks.add(currentMino.block[1]);
            staticBlocks.add(currentMino.block[2]);
            staticBlocks.add(currentMino.block[3]);

            if (currentMino.block[0].y < top_y || currentMino.block[1].y < top_y ||
                    currentMino.block[2].y < top_y || currentMino.block[3].y < top_y) {
                gameOver = true;
                return;
            }

            currentMino.deactivate = false;
            //replace the current mino with the next mino

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = randomMino();
            nextMino.setXY(NEXT_MINO_START_X, NEXT_MINO_START_Y);

            if (checkSpawnCollision()) {
                gameOver = true;
                return;
            }

            checkDelete();
        } else if (!gameOver) {
            currentMino.update();
        }
    }

    private boolean checkSpawnCollision() {
        for (int i = 0; i < staticBlocks.size(); i++) {
            for (int j = 0; j < currentMino.block.length; j++) {
                if (currentMino.block[j].x == staticBlocks.get(i).x &&
                        currentMino.block[j].y == staticBlocks.get(i).y) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int rowCount = 0;
        int linesDeletedThisCheck = 0;

        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    // increase the count if there is a static block
                    rowCount++;
                }
            }
            x += Block.SIZE;

            if (x == right_x) {
                if (rowCount == 12) {
                    linesDeletedThisCheck++;
                    // this means that the row is filled with bocks, so we can now delet the row
                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }
                    // now the row above the deleted row should slide down
                    for (int i = 0; i < staticBlocks.size(); i++) {

                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                rowCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        if (linesDeletedThisCheck > 0) {
            linesCleared += linesDeletedThisCheck;
            switch (linesDeletedThisCheck) {
                case 1:
                    score += 100;
                    break;
                case 2:
                    score += 300;
                    break;
                case 3:
                    score += 500;
                    break;
                case 4:
                    score += 800;
                    break;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGame() {
        staticBlocks.clear();
        score = 0;
        linesCleared = 0;
        gameOver = false;
        currentMino = randomMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = randomMino();
        nextMino.setXY(NEXT_MINO_START_X, NEXT_MINO_START_Y);
    }

    public void draw(Graphics2D g1) {
        //draw main play area
        g1.setColor(Color.WHITE);
        g1.setStroke(new BasicStroke(4f));
        g1.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);
        int x = right_x + 100;
        int y = bottom_y - 200;
        g1.drawRect(x, y, 200, 200);
        g1.setFont(new Font("Arial", Font.PLAIN, 30));
        g1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g1.drawString("NEXT", x + 60, y + 60);

        //Draw the currentMino
        if (currentMino != null) {
            currentMino.draw(g1);
        }

        //Draw the next Mino
        nextMino.draw(g1);

        // Draw Static Blocks
        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g1);
        }

        // Draw Score
        g1.setColor(Color.WHITE);
        g1.setFont(new Font("Arial", Font.PLAIN, 25));
        g1.drawString("SCORE", left_x - 150, top_y + 50);
        g1.setFont(new Font("Arial", Font.BOLD, 30));
        g1.setColor(Color.YELLOW);
        g1.drawString(String.valueOf(score), left_x - 150, top_y + 90);

        g1.setColor(Color.WHITE);
        g1.setFont(new Font("Arial", Font.PLAIN, 25));
        g1.drawString("LINES", left_x - 150, top_y + 150);
        g1.setFont(new Font("Arial", Font.BOLD, 30));
        g1.setColor(Color.CYAN);
        g1.drawString(String.valueOf(linesCleared), left_x - 150, top_y + 190);

        // Draw Pause
        g1.setColor(Color.yellow);
        g1.setFont(g1.getFont().deriveFont(50f));
        if (KeyHandler.pausePressed) {
            x = left_x + 115;
            y = top_y + 320;
            g1.drawString("Pause", x, y);
        }

        // Draw Game Over
        if (gameOver) {
            g1.setColor(Color.RED);
            g1.setFont(new Font("Arial", Font.BOLD, 60));
            x = left_x + 30;
            y = top_y + 300;
            g1.drawString("GAME OVER", x, y);

            g1.setColor(Color.WHITE);
            g1.setFont(new Font("Arial", Font.PLAIN, 25));
            g1.drawString("Final Score: " + score, x + 50, y + 50);
        }
    }


}
