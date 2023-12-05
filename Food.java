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
        int x = random.nextInt(maxWidth);
        int y = random.nextInt(maxHeight);

        this.position = new Point(x, y);
    }
}
