package tetromino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

/**
 * A Mino class.
 * <p>
 * Purdue University -- CS18000 -- Fall 2025 -- Honors Project
 * Java class Mino.java uses 4 bocks to define and build a mino.
 *
 * @author Harsh Anand Purdue CS
 * @version December 5, 2025
 */


public class Mino {
    public Block block[] = new Block[4];
    public Block b[] = new Block[4];
    int autoDropCounter = 0; // the number of times mino drops
    public int direction = 1; // There are 4 directions (1/2/3/4)
    boolean leftCollision, rightCollision, downCollision;
    public boolean active = true;
    int deactivationCounter = 0;
    public boolean deactivate;


    public void create(Color color) {
        block[0] = new Block(color);
        block[1] = new Block(color);
        block[2] = new Block(color);
        block[3] = new Block(color);
        b[0] = new Block(color);
        b[1] = new Block(color);
        b[2] = new Block(color);
        b[3] = new Block(color);
    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {
        checkRotationCollision();

        if (rightCollision == false && leftCollision == false && downCollision == false) {

            this.direction = direction;
            block[0].x = b[0].x;
            block[0].y = b[0].y;
            block[1].x = b[1].x;
            block[1].y = b[1].y;
            block[2].x = b[2].x;
            block[2].y = b[2].y;
            block[3].x = b[3].x;
            block[3].y = b[3].y;
        }
    }

    public void getDirection1() {
    }

    public void getDirection2() {
    }

    public void getDirection3() {
    }

    public void getDirection4() {
    }

    public void checkMotionCollision() {

        leftCollision = false;
        rightCollision = false;
        downCollision = false;

        checkBlockCollision();


        // Check collision on Left wall
        for (int i = 0; i < block.length; i++) {
            if (block[i].x == PlayManager.left_x) {
                leftCollision = true;
            }
        }
        // Check collision on Left wall
        for (int i = 0; i < block.length; i++) {
            if (block[i].x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
            }
        }
        for (int i = 0; i < block.length; i++) {
            if (block[i].y + Block.SIZE == PlayManager.bottom_y) {
                downCollision = true;
            }
        }

    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        downCollision = false;

        checkBlockCollision();

        // Check collision on Left wall
        for (int i = 0; i < block.length; i++) {
            if (b[i].x < PlayManager.left_x) {
                leftCollision = true;
            }
        }
        // Check collision on Right wall
        for (int i = 0; i < block.length; i++) {
            if (b[i].x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }
        }
        // Check collision on Bottom wall

        for (int i = 0; i < block.length; i++) {
            if (b[i].y + Block.SIZE > PlayManager.bottom_y) {
                downCollision = true;
            }
        }
    }

    private void checkBlockCollision() {
        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            //check down collision with other blocks
            for (int j = 0; j < block.length; j++) {
                if (block[j].y + Block.SIZE == targetY && block[j].x == targetX) {
                    downCollision = true;
                }
            }
            // check left collision with other blocks

            for (int j = 0; j < block.length; j++) {
                if (block[j].x - Block.SIZE == targetX && block[j].y == targetY) {
                    leftCollision = true;
                }
            }

            // check right collision with other blocks
            for (int j = 0; j < block.length; j++) {
                if (block[j].x + Block.SIZE == targetX && block[j].y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    public void update() {

        if (deactivate == true) {
            deactivating();
        }
        // move the mino
        if (KeyHandler.upPressed) {
            switch ((direction)) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyHandler.upPressed = false;
        }
        checkMotionCollision();
        if (KeyHandler.downPressed) {
            if (downCollision == false) {
                block[0].y += Block.SIZE;
                block[1].y += Block.SIZE;
                block[2].y += Block.SIZE;
                block[3].y += Block.SIZE;
            }

            // when moved down, reset the autoDropCounter

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed) {
            if (leftCollision == false) {
                block[0].x -= Block.SIZE;
                block[1].x -= Block.SIZE;
                block[2].x -= Block.SIZE;
                block[3].x -= Block.SIZE;
            }
            KeyHandler.leftPressed = false;

        }
        if (KeyHandler.rightPressed) {
            if (rightCollision == false) {
                block[0].x += Block.SIZE;
                block[1].x += Block.SIZE;
                block[2].x += Block.SIZE;
                block[3].x += Block.SIZE;
            }
            KeyHandler.rightPressed = false;

        }

        if (downCollision) {
            deactivate = true;

        } else {
            autoDropCounter++; // counter increases in every frame
            if (autoDropCounter == PlayManager.dropInterval) {
                // the mino goes down
                block[0].y += Block.SIZE;
                block[1].y += Block.SIZE;
                block[2].y += Block.SIZE;
                block[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }
    }

    private void deactivating() {

        deactivationCounter++;

        // Wait for 45 frames

        if (deactivationCounter == 45) {
            deactivationCounter = 0;
            checkMotionCollision();

            if (downCollision) {
                active = false;
            }

        }

    }

    public void draw(Graphics2D g1) {
        int margin = 2;
        g1.setColor(block[0].color);
        g1.fillRect(block[0].x + margin, block[0].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g1.fillRect(block[1].x + margin, block[1].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g1.fillRect(block[2].x + margin, block[2].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
        g1.fillRect(block[3].x + margin, block[3].y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);
    }
}
