// Snake.java

import java.awt.*;
import java.util.*;

/**
 * This class is designed to managing the Snake's state within the game. It
 * deals with its position, direction, and length.
 *
 * @author Michael Nguyen
 * @version Dec 4th, 2023
 **/
public class Snake
{
    public static final int SEGMENT_SIZE = 20;
    public enum Direction { UP, DOWN, LEFT, RIGHT };
    private Direction direction;
    private final Point head;
    private final ArrayList<Point> body;
    private Point newTail;  // Stores the position of where the last segment of the snake's body was


    public Snake()
    {
        this.direction = Direction.RIGHT;
        this.head = createHead();

        this.body = new ArrayList<>();
        this.body.add(new Point(this.head.x, this.head.y));
        this.body.add(new Point(this.head.x - SEGMENT_SIZE, this.head.y - SEGMENT_SIZE));
    }


    // Getter and Setter Methods
    public Point getHead()
    {
        return this.head;
    }


    public ArrayList<Point> getBody()
    {
        return this.body;
    }


    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }


    public void move()
    {
        // Store position of the last segment
        this.newTail = new Point(body.get(body.size() - 1));

        // Move the head
        switch (direction)
        {
            case UP     -> head.y -= SEGMENT_SIZE;
            case DOWN   -> head.y += SEGMENT_SIZE;
            case LEFT   -> head.x -= SEGMENT_SIZE;
            case RIGHT  -> head.x += SEGMENT_SIZE;
        }

        for (int i = body.size() - 1; i > 0; i--)
        {
            body.set(i, new Point(body.get(i - 1)));
        }

        this.body.set(0, new Point(head));
    }


    public void grow()
    {
        switch (direction)
        {
            case UP     ->  { this.newTail.y += SEGMENT_SIZE; }
            case DOWN   ->  { this.newTail.y -= SEGMENT_SIZE; }
            case LEFT   ->  { this.newTail.x += SEGMENT_SIZE; }
            case RIGHT  ->  { this.newTail.x -= SEGMENT_SIZE; }
        }

        // Add the new segment at the stored tail position
        body.add(newTail);
    }


    // Helper Methods

    /**
     * This helper method returns a Point object near the middle of the game window.
     *
     * @return      Point Object representing the coordinates of the snake head.
     */
    private Point createHead()
    {
        int x = (SnakeGame.WIDTH / 2);
        int y = (SnakeGame.HEIGHT / 2);

        return new Point(x, y);
    }
}
