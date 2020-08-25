package com.avijitsamanta.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    private Image food, tail, head;
    private int dots;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    private final int DOT_SIZE = 10;  // 300 * 300 = 90000 / 100= 900
    private final int TOTAL_DOT = (WIDTH * HEIGHT) / 50;
    private static final int RANDOM_POSITION = 20; // we can chose 1 to 30 because screen size is 300 x 300

    private int food_x;
    private int food_y;

    private final int[] x = new int[TOTAL_DOT];
    private final int[] y = new int[TOTAL_DOT];

    private Timer timer;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean isPlaying = true;

    public Board() {
        addKeyListener(new MyKeyAdopter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        loadImages();
        initGame();
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("com/avijitsamanta/SnakeGame/icons/apple.png"));
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("com/avijitsamanta/SnakeGame/icons/dot.png"));
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("com/avijitsamanta/SnakeGame/icons/head.png"));

        food = i1.getImage();
        tail = i2.getImage();
        head = i3.getImage();
    }

    public void initGame() {
        dots = 3; // initial 2 tail and one 1 head
        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;  //  x axis 1 dot=50, 2 dot=50-10=40, 3 dot=50-20=30
            y[i] = 50;  //  y axis
        }

        locateFood();
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateFood() {
        int r = (int) (Math.random() * RANDOM_POSITION);  // 0 to 1 => 0.6 * 20 = 12
        food_x = r * DOT_SIZE; // 12 * 10 =120
        r = (int) (Math.random() * RANDOM_POSITION);
        food_y = r * DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isPlaying) {
            checkFood();
            checkCollision();
            move();
        }
        repaint();
    }

    public void move() {
        if (!isPlaying)
            return;
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {  // Y axis is same
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {  // y axis is same
            x[0] += DOT_SIZE;
        }

        if (upDirection)  // x axis is same
            y[0] -= DOT_SIZE;
        if (downDirection)  // x axis is same
            y[0] += DOT_SIZE;
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                isPlaying = false;
                break;
            }
        }

        if (y[0] >= HEIGHT || y[0] < 0 || x[0] >= WIDTH || x[0] < 0)
            isPlaying = false;

        if (!isPlaying)
            timer.stop();
    }

    public void checkFood() {
        if (x[0] == food_x && y[0] == food_y) {
            dots++;
            locateFood();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (isPlaying) {
            g.drawImage(food, food_x, food_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {  // head
                    g.drawImage(head, x[i], y[i], this);
                } else
                    g.drawImage(tail, x[i], y[i], this);
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF", Font.BOLD, 16);
        FontMetrics matrices = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.drawString(msg, (WIDTH - matrices.stringWidth(msg)) / 2, HEIGHT / 2);
    }

    private class MyKeyAdopter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                leftDirection = false;
                rightDirection = false;
                upDirection = true;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }
        }
    }
}
