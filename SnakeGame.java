// SnakeGame.java

import javax.swing.*;

/**
 *
 * @author Michael Nguyen
 * @version Last Modified Dec 4th, 2023
 **/
public class SnakeGame
{
    public static void main(String[] args)
    {
        // Boilerplate
        JFrame frame = new JFrame("Snake");
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        frame.setVisible (true);
    }
}

// main instantiates an instance of a class that extends JFrame and the actual work begins in that instance's constructor.
// main create a JFrame object and the actual work begins with main operating on that object.