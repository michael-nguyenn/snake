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
    private Point head;
    private ArrayList<Point> body;
    private Direction direction;


    public Snake()
    {
        int x = (SnakeGame.WIDTH / 2) - (SEGMENT_SIZE / 2);
        int y = (SnakeGame.HEIGHT / 2) - (SEGMENT_SIZE / 2);
        this.head = new Point(x, y);
        this.direction = Direction.RIGHT;
    }


    // Getter and Setter Methods
    public Point getHead()
    {
        return this.head;
    }


    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }


    public void move()
    {
        switch (direction)
        {
            case UP     ->  { this.head.y--; }
            case DOWN   ->  { this.head.y++; }
            case LEFT   ->  { this.head.x--; }
            case RIGHT  ->  { this.head.x++; }
        }
    }


    public void grow()
    {
        System.out.println("It's growinggg;");
    }
}
