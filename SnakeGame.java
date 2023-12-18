// SnakeGame.java

import javax.swing.*;
import java.awt.*;


/**
 * The SnakeGame class acts as the main controller of the game.
 * It sets up the game's main frame and manages the transition
 * between the SettingsWindow and the GameWindow.
 *
 * @author Michael Nguyen
 * @version Last Modified Dec 18th, 2023
 */
public class SnakeGame
{
    private final JFrame frame;                     // Main frame for the game
    private final SettingsWindow settingsWindow;    // Window for SettingsWindow


    /**
     * Constructor for SnakeGame. Sets up the main game frame and
     * displays the settings window.
     */
    public SnakeGame()
    {
        frame = new JFrame("Snake");

        settingsWindow = new SettingsWindow(this);
        frame.add(settingsWindow);

        // Boilerplate frame setup
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible (true);
    }


    /**
     * Starts the game with the specified settings. It initializes the game window
     * and the scoreboard, and switches the view from the settings window to the game.
     *
     * @param settings Game settings selected by the user.
     */
    public void startGame(GameSettings settings) {
        settingsWindow.setVisible(false);

        // Initialize and add the game window and scoreboard with the settings
        ScoreBoard scoreBoard = new ScoreBoard(settings);
        GameWindow gameWindow = new GameWindow(settings, scoreBoard);

        frame.getContentPane().removeAll();                     // Clear previous components
        frame.add(scoreBoard, BorderLayout.NORTH);
        frame.add(gameWindow, BorderLayout.CENTER);

        frame.setSize(settings.getWidth(), settings.getHeight()
                + scoreBoard.getPreferredSize().height);
        frame.setLocationRelativeTo(null);                      // Re-center the window
        gameWindow.requestFocusInWindow();                      // Focus so keyboard inputs are read
    }
}
