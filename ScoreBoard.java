// ScoreBoard.java

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * The ScoreBoard class is responsible for displaying and managing the current score
 * and the high score within the game. It reads and updates the high score from a file.
 *
 * @author Michael Nguyen
 * @version Last Modified Dec 10th, 2023
 */
public class ScoreBoard extends JPanel
{
    private GameSettings settings;
    private JLabel currentScoreLabel;
    private JLabel highScoreLabel;
    private int highScore;
    private int score;


    public ScoreBoard(GameSettings settings)
    {
        this.settings = settings;
        this.score = 0;
        this.highScore = readHighScore(); // Read the initial high score from file
        this.setBackground(Color.LIGHT_GRAY);

        // Current score label on the left (west)
        currentScoreLabel = new JLabel("Score: 0");
        add(currentScoreLabel, BorderLayout.WEST);

        // High score label on the right (east)
        highScoreLabel = new JLabel("High Score: " + highScore);
        add(highScoreLabel, BorderLayout.EAST);

        this.setPreferredSize(new Dimension(settings.getWidth(), 30));
    }


    /**
     * @return The current score.
     */
    public int getScore() { return this.score; }


    /**
     * Updates the score label.
     *
     * @param score The new score value.
     */
    public void setScore(int score)
    {
        currentScoreLabel.setText("Score: " + score);
    }


    /**
     * Reads the high score from a file
     *
     * @return The high score read from the file or 0 if not found.
     */
    private int readHighScore()
    {
        try {

            File file = new File("highscore.txt");

            Scanner scanner = new Scanner(file);

            int highScore = scanner.hasNextInt() ? scanner.nextInt() : 0;
            scanner.close();

            return highScore;

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * Writes the high score to a file.
     *
     * @param highScore The high score to write.
     */
    private void writeHighScore(int highScore)
    {
        try
        {
            PrintWriter writer = new PrintWriter("highscore.txt");

            writer.println(highScore);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Checks and updates the high score. If the provided score is higher than the current high score,
     * updates the high score label and writes the new high score to the file.
     *
     * @param score The current score to check against the high score.
     */
    public void checkAndUpdateHighScore(int score)
    {
        if (score > highScore)
        {
            highScore = score;
            highScoreLabel.setText("High Score: " + highScore);
            writeHighScore(highScore); // Update high score file
        }
    }
}
