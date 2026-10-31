package tetromino;

import java.awt.*;

/**
 * The Block class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class Block.java defines all the features of the blocks used for building each mino.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;
    public Color color;

    public Block(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g1) {
        int margin = 2;
        g1.setColor(color);
        g1.fillRect(x + margin, y + margin, SIZE - (margin * 2), SIZE - (margin * 2));
    }


}
