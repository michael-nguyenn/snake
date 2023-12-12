// SnakeGame.java

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Michael Nguyen
 * @version Last Modified Dec 4th, 2023
 **/
public class SnakeGame
{
    // Constants
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;


    public static void main(String[] args)
    {
        // Boilerplate
        JFrame frame = new JFrame("Snake");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);

        ScoreBoard scoreBoard = new ScoreBoard();
        GameWindow gameWindow = new GameWindow(scoreBoard);


        frame.add(gameWindow, BorderLayout.CENTER);
        frame.add(scoreBoard, BorderLayout.NORTH);
        frame.setVisible (true);

    }
}