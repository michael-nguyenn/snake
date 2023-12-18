import javax.swing.*;

/**
 * StartGame serves as the entry point for the Extreme Snake Game
 *
 * @author Michael Nguyen
 * @version Last modified Dec 16th, 2023
 **/
public class StartGame
{
    public static void main (String[] args)
    {
        // invokeLater() is used to ensure that all AWT events are processed.
        SwingUtilities.invokeLater(new Runnable()
        {
            // Create and start the SnakeGame instance
            public void run()
            {
                new SnakeGame();
            }
        });
    }
}

