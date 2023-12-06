// GameWindow.java

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 *
 * @author Michael Nguyen
 * @version Last modified Dec 4th, 2023
 **/
public class GameWindow extends JPanel
{
    public static final int GAME_DELAY = 100;
    private Timer timer;
    private Snake snake;
    private Food food;


    public GameWindow()
    {
        this.snake = new Snake();
        this.food = new Food(SnakeGame.WIDTH, SnakeGame.HEIGHT);
        this.generateFoodPosition();

        this.setPreferredSize(new Dimension(SnakeGame.WIDTH, SnakeGame.HEIGHT));
        this.setFocusable(true);

        this.timer = addTimer();
        this.timer.start();

        this.addListeners();
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);


        for (Point segment : snake.getBody())
        {
            g.setColor(Color.BLACK);
            g.fillRect(segment.x, segment.y, Snake.SEGMENT_SIZE, Snake.SEGMENT_SIZE);
        }

        g.setColor(Color.PINK);

        Point foodPosition = food.getPosition();
        g.fillRect(foodPosition.x, foodPosition.y, 20, 20);
    }


    private Timer addTimer()
    {
        return new Timer(GAME_DELAY, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                snake.move();

                Point head = snake.getHead();
                Point foodPosition = food.getPosition();
                ArrayList<Point> body = snake.getBody();

                double foodDistance = distanceBetweenPoints(head.x, head.y, foodPosition.x, foodPosition.y);

                if (foodDistance <= Snake.SEGMENT_SIZE)
                {
                    snake.grow();
                    generateFoodPosition();
                }

                // Checking collision with walls
                if (head.x < Snake.SEGMENT_SIZE|| head.x >= SnakeGame.WIDTH - Snake.SEGMENT_SIZE
                        || head.y < Snake.SEGMENT_SIZE || head.y >= SnakeGame.HEIGHT - (2 * Snake.SEGMENT_SIZE))
                {
                    timer.stop();
                }

                // Checking collision with self
                for (int i = 1; i < body.size(); i++)
                {
                    if (head.equals(body.get(i)))
                    {
                        timer.stop();
                        break;
                    }
                }

                repaint();
            }
        });
    }


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
    private void generateFoodPosition()
    {
        do
        {
            food.generateNewPosition(SnakeGame.WIDTH, SnakeGame.HEIGHT);
        }
        while (snake.getBody().contains(food.getPosition()));
    }


    private double distanceBetweenPoints(int x1, int y1, int x2, int y2)
    {
        // The distance between two points was used with the formula:
            // Square Root of (x2 - x1)^2 + (y2 - y1)^2

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
