package com.avijitsamanta.SnakeGame;

import javax.swing.*;

public class Snake extends JFrame {

    public Snake() {
        add(new Board());
        pack();  // set the frame
        setLocationRelativeTo(null);  // it set the frame location in center
        setTitle("Snake Game"); // set the title of a frame
        setResizable(false);  // set do not resize
    }

    public static void main(String[] args) {
        new Snake().setVisible(true);
    }

}
