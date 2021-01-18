package com.avijitsamanta.NewSnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private ImageIcon titleImage;
    private final int[] snakeXLength = new int[750];
    private final int[] snakeYLength = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;
    private int moves = 0;
    private static final int DOT_SIZE = 25;

    private final int lengthOfSnake = 3;

    private Timer timer;
    private int delay = 150;

    private ImageIcon snakeImage;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
    }

    @Override
    public void paint(Graphics g) {
        if (moves == 0) {
            snakeXLength[2] = 50;
            snakeXLength[1] = 75;
            snakeXLength[0] = 100;

            snakeYLength[2] = 100;
            snakeYLength[1] = 100;
            snakeYLength[0] = 100;
        }


        // draw title image border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);

        // draw title image
        titleImage = new ImageIcon(ClassLoader
                .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/snaketitle.jpg"));
        titleImage.paintIcon(this, g, 25, 11);

        // draw border game play
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 851, 577);

        // draw background for game play
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);


        rightMouth = new ImageIcon(ClassLoader
                .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/rightmouth.png"));
        rightMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);

        for (int a = 0; a < lengthOfSnake; a++) {
            if (a == 0) {  // head
                if (right) {
                    rightMouth = new ImageIcon(ClassLoader
                            .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/rightmouth.png"));
                    rightMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
                } else if (left) {
                    leftMouth = new ImageIcon(ClassLoader
                            .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/leftmouth.png"));
                    leftMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
                } else if (up) {
                    upMouth = new ImageIcon(ClassLoader
                            .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/upmouth.png"));
                    upMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
                } else if (down) {
                    downMouth = new ImageIcon(ClassLoader
                            .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/downmouth.png"));
                    downMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
                }
            } else {
                snakeImage = new ImageIcon(ClassLoader
                        .getSystemResource("com/avijitsamanta/NewSnakeGame/icons/snakeimage.png"));
                snakeImage.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (right) {
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                snakeYLength[r + 1] = snakeYLength[r];
            }
            for (int r = lengthOfSnake - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeXLength[r] = snakeXLength[r] + DOT_SIZE;
                } else {
                    snakeXLength[r] = snakeXLength[r - 1];
                }

                if (snakeXLength[r] > 850) {   //  moving out side of screen
                    snakeXLength[r] = 25;
                }
            }
        }
        if (left) {

        }
        if (up) {

        }
        if (down) {

        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        timer.start();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
            } else {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
