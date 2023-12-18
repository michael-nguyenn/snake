// GameWindow.java

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * GameWindow class is the main graphical user interface for the game.
 * It handles the game logic, rendering of the snake, consuming apples, and game over
 * mechanics
 *
 * @author Michael Nguyen
 * @version Last Modified Dec 17th, 2023
 */
public class GameWindow extends JPanel
{
    private GameSettings settings;
    private Timer timer;                            // Timer for game loop
    private Snake snake;
    private ConsumableFood food;                    // Current consumable food
    private ArrayList<PoisonFood> poisonApples;     // ArrayList of poison apples on the field
    private ScoreBoard scoreBoard;
    private int score;                              // Current Score
    private int currentDelay;                       // Current Game Delay (Lower is quicker)
    private int lastMilestone;                      // Every 50 is a "milestone"



    /**
     * Constructor initializes the game.
     *
     * @param   settings        The game settings
     * @param   scoreBoard      The scoreboard
     */
    public GameWindow(GameSettings settings, ScoreBoard scoreBoard)
    {
        this.setPreferredSize(new Dimension(settings.getWidth(), settings.getHeight()));
        this.setFocusable(true);

        this.settings = settings;
        this.currentDelay = settings.getGameDelay();
        this.lastMilestone = 0;

        // Initialize ScoreBoard Object and get the score
        this.scoreBoard = scoreBoard;
        this.score = scoreBoard.getScore();

        // Sets up the snake, food, and poison apple ArrayList
        this.snake = new Snake(settings);
        this.food = new ConsumableFood(settings);
        this.poisonApples = new ArrayList<>();
        this.generateFoodPosition();

        // Create and start timer
        this.timer = addTimer();
        this.timer.start();

        this.addListeners();
    }


    /**
     * Paints the game components including the snake, food, and poison apples.
     *
     * @param   g   Graphics object for drawing.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Draw snake
        for (Point segment : snake.getBody())
        {
            g.setColor(settings.getSnakeColor());
            g.fillRect(segment.x, segment.y, settings.getSegmentSize(), settings.getSegmentSize());
        }

        // Draw apples
        Point foodPosition = food.getPosition();
        g.drawImage(food.getFoodImage(), foodPosition.x, foodPosition.y, this);

        // Draw poison apples
        for (PoisonFood poisonApple : poisonApples)
        {
            Point poisonPosition = poisonApple.getPosition();
            g.drawImage(poisonApple.getFoodImage(), poisonPosition.x, poisonPosition.y, this);
        }
    }


    /**
     * Creates and returns a Timer instance for the game loop.
     * @return  Timer object for game loop
     */
    private Timer addTimer()
    {
        return new Timer(settings.getGameDelay(), new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                snake.move();

                Point head = snake.getHead();

                // Food Consumption
                detectFoodCollision(head);

                // Checking collision with walls
                detectWallCollision(head);

                // Checking collision with self
                detectSelfCollision(head);

                // Checking collision with poison apples
                detectPoisonCollision(head);

                repaint();
            }
        });
    }


    /**
     * Adds key listeners for keyboard input
     */
    private void addListeners()
    {
        this.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                switch(e.getKeyCode())
                {
                    case KeyEvent.VK_UP     -> snake.setDirection(Snake.Direction.UP);
                    case KeyEvent.VK_DOWN   -> snake.setDirection(Snake.Direction.DOWN);
                    case KeyEvent.VK_LEFT   -> snake.setDirection(Snake.Direction.LEFT);
                    case KeyEvent.VK_RIGHT  -> snake.setDirection(Snake.Direction.RIGHT);
                }
            }
        });
    }

    // Helper Methods

    /**
     * Detects collision between the snake and food. On contact, snake will grow,
     * score increments depending on apple type, and game speed is adjusted if a milestone is reached.
     * This method will also generate a poison apple at its set probability, and spawn a new apple.
     *
     * @param   head    The current head position of the snake
     */
    private void detectFoodCollision(Point head)
    {
        Random random = new Random();
        Point foodPosition = food.getPosition();
        double foodDistance = distanceBetweenPoints(head.x, head.y, foodPosition.x, foodPosition.y);

        if (foodDistance <= settings.getSegmentSize())
        {
            snake.grow();
            increaseScore(food.getAppleType());
            scoreBoard.setScore(score);

            // Check if a new milestone (every 50 points) is reached
            checkMilestoneReached();


            food = new ConsumableFood(settings);
            generateFoodPosition();

            if (random.nextDouble() < settings.getPoisonProbability()) { generatePoisonApple(); }
        }
    }


    /**
     * Checks if a new score milestone is reached and increases the snake's speed.
     */
    private void checkMilestoneReached()
    {
        if (score / 50 > lastMilestone)
        {
            lastMilestone = score / 50;
            increaseSpeed();
        }
    }


    /**
     * Detects collision of the snake with the game boundaries (walls).
     * @param   head    The current head position of the snake
     */
    private void detectWallCollision(Point head)
    {
        if (head.x <= 0
                || head.x >= settings.getWidth()
                || head.y < 0
                || head.y >= settings.getHeight() - settings.getSegmentSize())
        {
            handleGameOver(getGameEndMessage());
        }
    }


    /**
     * Detects collision of the snake with itself.
     * @param   head    The current head position of the snake
     */
    private void detectSelfCollision(Point head)
    {
        ArrayList<Point> body = snake.getBody();

        for (int i = 1; i < body.size(); i++)
        {
            if (head.equals(body.get(i)))
            {
                handleGameOver(getGameEndMessage());
                break;
            }
        }
    }


    /**
     * Detects collision of the snake with any of the poison apples.
     * @param   head    The current head position of the snake
     */
    private void detectPoisonCollision(Point head)
    {
        for (PoisonFood poisonApple : poisonApples)
        {
            Point poisonPosition = poisonApple.getPosition();
            double poisonDistance = distanceBetweenPoints(
                    head.x, head.y, poisonPosition.x, poisonPosition.y);

            if (poisonDistance < settings.getSegmentSize())
            {
                handleGameOver(getGameEndMessage());
                break;
            }
        }
    }


    /**
     * Generates a new position for the food within the game boundaries.
     */
    private void generateFoodPosition()
    {
        do
        {
            food.generateNewPosition(settings.getWidth() - 2 * settings.getSegmentSize(),
                    settings.getHeight() - 2 * settings.getSegmentSize());
        }
        while (isPositionOccupied(food.getPosition()));
    }


    /**
     * Generates a new poison apple and adds it to the game.
     */
    private void generatePoisonApple()
    {
        PoisonFood newPoisonApple;

        do
        {
            newPoisonApple = new PoisonFood(settings);

            newPoisonApple.generateNewPosition(settings.getWidth() - 2 * settings.getSegmentSize(),
                    settings.getHeight() - 2 * settings.getSegmentSize());
        }
        while (isPositionOccupied(newPoisonApple.getPosition()));

        poisonApples.add(newPoisonApple);
    }


    /**
     * Calculates the distance between two points.
     * @param   x1  x-coordinate of the first point
     * @param   y1  y-coordinate of the first point
     * @param   x2  x-coordinate of the second point
     * @param   y2  y-coordinate of the second point
     * @return      The distance between the two points
     */
    private double distanceBetweenPoints(int x1, int y1, int x2, int y2)
    {
        // The distance between two points was used with the formula:
            // Square Root of (x2 - x1)^2 + (y2 - y1)^2

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    /**
     * Increases the score based on the type of apple consumed.
     * @param   appleType   Type of the apple consumed
     */
    private void increaseScore(ConsumableFood.AppleType appleType)
    {
        switch (appleType)
        {
            case REGULAR -> { this.score++; }
            case RARE -> { this.score += 5; }
            case GOLDEN ->
            {
                this.score += 20;
                poisonApples.clear();
            }
        }
    }


    /**
     * Checks if a given position is already occupied by the snake's body or poison apples.
     * @param position Position to check
     * @return True if the position is occupied, false otherwise
     */
    private boolean isPositionOccupied(Point position)
    {
        // Check if the position is occupied by the snake's body
        for (Point segment : snake.getBody())
        {
            if (segment.equals(position)) { return true; }
        }

        // Check if the position is occupied by any poison apple
        if (poisonApples != null)
        {
            for (PoisonFood poisonApple : poisonApples)
            {
                if (poisonApple.getPosition().equals(position)) { return true; }
            }
        }
        // Means the position is not occupied
        return false;
    }


    /**
     * Increases the speed of the snake. Game delay will never drop below 30. (Would be too fast)
     */
    private void increaseSpeed()
    {
        if (currentDelay > 30)
        {
            this.currentDelay = currentDelay - 10;
            timer.setDelay(currentDelay);
        }
    }


    /**
     * Generates a game-ending message based on the current score.
     * @return  Game-ending message
     */
    private String getGameEndMessage()
    {
        if (this.score <= 50)
        {
            return "Are you even trying? Your score: " + this.score;
        }
        else if (this.score <= 100)
        {
            return "Not bad, kiddo! Your score: " + this.score;
        }
        else if (this.score <= 150)
        {
            return "You're getting the hang of this! Your score: " + this.score;
        }
        else if (this.score <= 200)
        {
            return "Impressive performance! Your score: " + this.score;
        }
        else
        {
            return "Snake master! Your score: " + this.score;
        }
    }


    /**
     * Handles the game over scenario, updating high score and showing game over dialog.
     * @param   message   Game over message to display
     */
    private void handleGameOver(String message)
    {
        scoreBoard.checkAndUpdateHighScore(score);

        // Custom buttons
        Object[] options = { "Play Again", "Exit" };

        int choice = JOptionPane.showOptionDialog(this, message, "Womp Womp",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Handle the player's choice
        if (choice == JOptionPane.YES_OPTION) { restartGame(); }
        else { System.exit(0); }

        timer.stop();
    }


    /**
     * Restarts the game by resetting all game components.
     */
    private void restartGame()
    {
        // Reset Settings
        this.currentDelay = settings.getGameDelay();
        this.lastMilestone = 0;

       // Reset the snake
        this.snake = new Snake(settings);
        this.generateFoodPosition();
        this.poisonApples.clear();

        this.scoreBoard.setScore(0);
        this.score = scoreBoard.getScore();

        this.timer = addTimer();
        this.timer.start();
    }
}