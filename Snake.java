// Snake.java

import java.awt.*;
import java.util.*;

/**
 * The Snake class is responsible for managing the state of the snake.
 * The class provides functionality to move and grow.
 *
 * @author Michael Nguyen
 * @version Dec 15th, 2023
 */
public class Snake
{
    public enum Direction { UP, DOWN, LEFT, RIGHT };
    private GameSettings settings;
    private Direction direction;
    private final Point head;
    private final ArrayList<Point> body;
    private Point newTail;  // Stores the position of where the last segment of the snake's body was


    public Snake(GameSettings settings)
    {
        this.settings = settings;
        this.direction = Direction.RIGHT;
        this.head = createHead();

        this.body = new ArrayList<>();

        // Start the snake off with its head and one extra segment.
        this.body.add(new Point(this.head.x, this.head.y));
        this.body.add(new Point(this.head.x - settings.getSegmentSize(),
                this.head.y - settings.getSegmentSize()));
    }

    // Getter Methods

    /**
     * @return The current position of the snake's head.
     */
    public Point getHead()
    {
        return this.head;
    }


    /**
     * @return The snake's body as an ArrayList of Point objects.
     */
    public ArrayList<Point> getBody()
    {
        return this.body;
    }


    // Setter

    /**
     * Sets the direction of the snake's movement.
     *
     * @param direction The new direction for the snake to move in.
     */
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }


    /**
     * Moves the snake in its current direction. Updates the position of the head and body segments.
     */
    public void move()
    {
        // Store position of the second last segment
        this.newTail = new Point(body.get(body.size() - 2));

        // Move the head
        switch (direction)
        {
            case UP     -> head.y -= settings.getSegmentSize();
            case DOWN   -> head.y += settings.getSegmentSize();
            case LEFT   -> head.x -= settings.getSegmentSize();
            case RIGHT  -> head.x += settings.getSegmentSize();
        }

        for (int i = body.size() - 1; i > 0; i--)
        {
            body.set(i, new Point(body.get(i - 1)));
        }

        this.body.set(0, new Point(head));
    }


    /**
     * Grows the snake by adding a new segment at the tail position.
     * The growth direction depends on the current movement direction.
     */
    public void grow()
    {
        switch (direction)
        {
            case UP     ->  { this.newTail.y += settings.getSegmentSize(); }
            case DOWN   ->  { this.newTail.y -= settings.getSegmentSize(); }
            case LEFT   ->  { this.newTail.x += settings.getSegmentSize(); }
            case RIGHT  ->  { this.newTail.x -= settings.getSegmentSize(); }
        }
        // Add the new segment at the stored tail position
        body.add(newTail);
    }


    /**
     * This helper method returns a Point object near the middle of the game window.
     *
     * @return      Point Object representing the coordinates of the snake head.
     */
    private Point createHead()
    {
        int x = (settings.getWidth() / 2);
        int y = (settings.getHeight() / 2);

        return new Point(x, y);
    }
}
