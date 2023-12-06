// Food.java

import java.awt.*;
import java.util.Random;

/**
 *
 * @author Michael Nguyen
 * @version Dec 5th, 2023
 **/
public class Food
{
    private Point position;
    private Random random;


    public Food(int maxWidth, int maxHeight)
    {
        this.random = new Random();
        generateNewPosition(maxWidth, maxHeight);
    }


    // Getter
    public Point getPosition()
    {
        return this.position;
    }


    public void generateNewPosition(int maxWidth, int maxHeight)
    {
        int min = Snake.SEGMENT_SIZE;
        maxWidth = maxWidth - Snake.SEGMENT_SIZE;
        maxHeight = maxHeight - (2 *Snake.SEGMENT_SIZE);

        int x = random.nextInt((maxWidth - min) + 1) + min;
        int y = random.nextInt((maxHeight - min) + 1) + min;

        this.position = new Point(x, y);
    }
}
