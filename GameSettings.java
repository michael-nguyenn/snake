// GameSettings.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Holds all the configuration settings for the game. This includes dimensions,
 * snake properties, apple probabilities, and game speed mechanics.
 *
 * @author Michael Nguyen
 * @version Last modified Dec 17th, 2023
 */
public class GameSettings
{
    // Game Properties
    private int width = 600;
    private int height = 600;
    private int segmentSize = 20;
    private double regularProbability = 0.7;
    private double rareProbability = 0.2;
    private double goldenProbability = 0.1;
    private Color snakeColor;
    private int gameDelay;
    private double poisonProbability;


    /**
     * Default constructor.
     */
    public GameSettings()
    {
        this(Color.BLACK, 100, 0.3);
    }


    /**
     * Constructor with customizable settings.
     *
     * @param   snakeColor              The color of the snake.
     * @param   gameDelay               The  delay (speed) of the game.
     * @param   poisonProbability       The probability of spawning a poison apple.
     */
    public GameSettings(Color snakeColor, int gameDelay, double poisonProbability)
    {
        this.snakeColor = snakeColor;
        this.gameDelay = gameDelay;
        this.poisonProbability = poisonProbability;
    }

    // Accessor methods

    /**
     * @return  Returns the width in pixels of the playing area
     */
    public int getWidth() { return this.width; };

    /**
     *
     * @return  Returns the height in pixels of the playing area
     */
    public int getHeight() { return this.height; }

    /**
     * @return Returns the delay in milliseconds
     */
    public int getGameDelay() { return this.gameDelay; }

    /**
     * @return  Returns the segment size of each snake body part
     */
    public int getSegmentSize() { return this.segmentSize; }

    /**
     * @return  Returns the probability of a regular (white) apple spawning
     */
    public double getRegularProbability() { return this.regularProbability; }

    /**
     * @return Returns the probability of a rare (red) apple spawning.
     */
    public double getRareProbability() { return this.rareProbability; }

    /**
     * @return  Returns the probability of a poison apple spawning
     */
    public double getPoisonProbability() { return this.poisonProbability; }

    /**
     * @return  Returns the color of the snake.
     */
    public Color getSnakeColor() { return this.snakeColor; }
}

/**
 * User interface for setting up the game configurations.
 * Allows users to select difficulty, snake color.
 * Interacts with the GameSettings, and SnakeGame class to apply these settings.
 *
 * @author Michael Nguyen
 * @version Last modified Dec 17th, 2023
 */
class SettingsWindow extends JPanel
{
    // Components
    private String[] snakeColorOptions = { "Black", "Blue", "Red", "Pink" };
    private Color[] snakeColors = { Color.BLACK, Color.BLUE, Color.RED, Color.PINK };
    private JLabel difficultyLabel, colorLabel;
    private JRadioButton easyButton, mediumButton, insaneButton;
    private ButtonGroup difficultyGroup;
    private JComboBox<String> colorDropdown;
    private JButton playButton, cancelButton;
    private SnakeGame snakeGame;


    /**
     * Constructor setting up the UI components and listeners.
     *
     * @param   snakeGame   The main game controller used to start the game.
     */
    public SettingsWindow(SnakeGame snakeGame)
    {
        this.snakeGame = snakeGame;
        this.layoutComponents();
        this.addActionListeners();
    }


    /**
     * Sets up the layout of the components using BoxLayout
     */
    private void layoutComponents()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Difficulty options
        difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton = new JRadioButton("Easy");
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton = new JRadioButton("Medium");
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        insaneButton = new JRadioButton("Insane");
        insaneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.setSelected(true);                               // Default Option

        // Group the radio buttons.
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyButton);
        difficultyGroup.add(mediumButton);
        difficultyGroup.add(insaneButton);

        // Color options
        colorLabel = new JLabel("Snake Color:");
        colorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorDropdown = new JComboBox<>(snakeColorOptions);

        // Play and Cancel buttons
        playButton = new JButton("Play Now");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Add components to the panel
        add(difficultyLabel);
        add(easyButton);
        add(mediumButton);
        add(insaneButton);
        add(colorLabel);
        add(colorDropdown);
        add(playButton);
        add(cancelButton);

        setVisible(true);
    }


    /**
     * Adds event listeners to the GUI components.
     */
    private void addActionListeners()
    {
        playButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                GameSettings settings = createGameSettings();
                snakeGame.startGame(settings);
            }
        });

        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae) { System.exit(0); }
        });
    }


    /**
     * Determines the difficulty setting based on selected radio button.
     *
     * @return  An integer representing the difficulty level.
     */
    private int getDifficultySettings()
    {
        if (easyButton.isSelected()) { return 0; }
        else if (mediumButton.isSelected() ) { return 1; }
        else { return 2; }
    }


    /**
     * Creates a new GameSettings object based on user inputs.
     *
     * @return GameSettings object with selected configurations.
     */
    private GameSettings createGameSettings()
    {
        Color snakeColor = snakeColors[colorDropdown.getSelectedIndex()];
        int difficulty = getDifficultySettings();

        if (difficulty == 0) { return new GameSettings(snakeColor, 100, 0.3); }
        else if (difficulty == 1) { return new GameSettings(snakeColor, 90, 0.4);}
        else { return new GameSettings(snakeColor, 80, 0.5); }
    }
}