package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * A Key Handler.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class KeyHandler.java deals with observing keys and also starting events in response to them.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;

        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if (pausePressed) {
                pausePressed = false;
            } else {
                pausePressed = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
