// Food.java

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * The abstract class Food serves as a base for different types of food items in the game.
 * It manages common properties such as position, image scaling, and random generation of food positions.
 *
 * @author Michael Nguyen
 * @version Dec 16th, 2023
 */
public abstract class Food
{
    protected GameSettings settings;
    protected Point position;
    protected Random random;
    protected String imageName;


    /**
     * Constructor
     */
    public Food(GameSettings settings)
    {
        this.settings = settings;
        this.random = new Random();
    }


    // Getter

    /**
     * Gets the current position of the apple.
     * @return  Point representing the position of the apple.
     */
    public Point getPosition() { return this.position; }


    /**
     * Generates an image for the apple with appropriate scaling.
     * @return Scaled image of the apple.
     */
    protected Image getFoodImage()
    {
        Image sourceImage = new ImageIcon("./images/" + this.imageName).getImage();

        // Calculate the new dimensions
        int scaledWidth = (int) (settings.getSegmentSize() * 1.5);
        int scaledHeight = (int) (settings.getSegmentSize() * 1.5);


        // Create a buffered image with the desired size
        BufferedImage scaledImage = new BufferedImage(
                scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = scaledImage.createGraphics();

        // Draw the source image into the buffered image with scaling
        graphics.drawImage(sourceImage, 0, 0, scaledWidth, scaledHeight, null);
        graphics.dispose();

        return scaledImage;
    }


    /**
     * Generates a random position for the food within specified bounds.
     * @param   maxWidth    Maximum width for apple position.
     * @param   maxHeight   Maximum height for apple position.
     */
    public void generateNewPosition(int maxWidth, int maxHeight)
    {
        int min = settings.getSegmentSize();
        int x = random.nextInt((maxWidth - min) + 1) + min;
        int y = random.nextInt((maxHeight - min) + 1) + min;

        this.position = new Point(x, y);
    }


    /**
     * Abstract method to get the image name for different types of apples.
     * @return String representing the image name.
     */
    abstract String getImageName();
}


/**
 * ConsumableFood is a subclass of Food representing edible items in the game.
 */
class ConsumableFood extends Food
{
    public enum AppleType { REGULAR, RARE, GOLDEN };
    private AppleType appleType;


    public ConsumableFood(GameSettings settings)
    {
        super(settings);
        this.appleType = determineAppleType();
        this.imageName = getImageName();
    }


    /**
     * Gets the type of apple.
     * @return AppleType enum representing the type of apple.
     */
    public AppleType getAppleType()
    {
        return this.appleType;
    }


    /**
     * Determines the type of apple based on preset probabilities.
     * @return AppleType enum representing the type of apple.
     */
    private AppleType determineAppleType()
    {
        // Random value between 0.0 and 1.0
        double randomValue = Math.random();

        // Given default values where regular is 0.7, rare is 0.2, and golden is 0.1

        // If randomValue < 0.70
        if (randomValue < settings.getRegularProbability()) { return AppleType.REGULAR; }
        // else if randomValue is between 0.70 - 0.90
        else if (randomValue < settings.getRegularProbability() + settings.getRareProbability())
        {
            return AppleType.RARE;
        }
        // else randomValue is 0.90 or above
        else { return AppleType.GOLDEN; }
    }


    /**
     * Provides the image name for the specific type of apple.
     * @return String representing the image name.
     */
    public String getImageName()
    {
        switch (appleType)
        {
            case REGULAR    -> { return "appleBasic.png"; }
            case RARE       -> { return "appleBetter.png"; }
            case GOLDEN     -> { return "appleGold.png"; }
            default         -> { return null; }
        }
    }
}

/**
 * PoisonFood is a subclass of Food representing poisonous food in the game.
 */
class PoisonFood extends Food
{
    public PoisonFood(GameSettings settings)
    {
        super(settings);
        this.imageName = getImageName();
    }


    /**
     * @return String representing the image name for poison apple.
     */
    public String getImageName()
    {
        return "applePoison.png";
    }
}


