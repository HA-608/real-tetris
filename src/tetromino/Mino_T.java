package tetromino;

import java.awt.*;

/**
 * A Mino T class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class Mino_T.java builds the T shaped mino.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class Mino_T extends Mino {
    public Mino_T() {
        create(Color.magenta);
    }

    public void setXY(int x, int y) {
        //   o
        // o o o

        block[0].x = x;
        block[0].y = y;
        block[1].x = block[0].x;
        block[1].y = block[0].y - Block.SIZE;
        block[2].x = block[0].x - Block.SIZE;
        block[2].y = block[0].y;
        block[3].x = block[0].x + Block.SIZE;
        block[3].y = block[0].y;
    }

    public void getDirection1() {
        //   o
        // o o o

        b[0].x = block[0].x;
        b[0].y = block[0].y;
        b[1].x = block[0].x;
        b[1].y = block[0].y - Block.SIZE;
        b[2].x = block[0].x - Block.SIZE;
        b[2].y = block[0].y;
        b[3].x = block[0].x + Block.SIZE;
        b[3].y = block[0].y;
        updateXY(1);
    }

    public void getDirection2() {
        // o
        // o o
        // o
        b[0].x = block[0].x;
        b[0].y = block[0].y;
        b[1].x = block[0].x + Block.SIZE;
        b[1].y = block[0].y;
        b[2].x = block[0].x;
        b[2].y = block[0].y - Block.SIZE;
        b[3].x = block[0].x;
        b[3].y = block[0].y + Block.SIZE;
        updateXY(2);

    }

    public void getDirection3() {
        // o o o
        //   o
        b[0].x = block[0].x;
        b[0].y = block[0].y;
        b[1].x = block[0].x;
        b[1].y = block[0].y + Block.SIZE;
        b[2].x = block[0].x + Block.SIZE;
        b[2].y = block[0].y;
        b[3].x = block[0].x - Block.SIZE;
        b[3].y = block[0].y;

        updateXY(3);
    }

    public void getDirection4() {
        //   o
        // o o
        //   o
        b[0].x = block[0].x;
        b[0].y = block[0].y;
        b[1].x = block[0].x - Block.SIZE;
        b[1].y = block[0].y;
        b[2].x = block[0].x;
        b[2].y = block[0].y + Block.SIZE;
        b[3].x = block[0].x;
        b[3].y = block[0].y - Block.SIZE;
        updateXY(4);
    }


}
