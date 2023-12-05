// GameWindow.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author Michael Nguyen
 * @version Last modified Dec 4th, 2023
 **/
public class GameWindow extends JPanel
{
    public static final int GAME_DELAY = 4;
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

        this.addTimer();
        this.timer.start();

        this.addListeners();
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(Color.PINK);

        Point head = snake.getHead();

        g.fillRect(head.x, head.y, Snake.SEGMENT_SIZE, Snake.SEGMENT_SIZE);

        Point foodPosition = food.getPosition();
        g.fillRect(foodPosition.x, foodPosition.y, 20, 20);
    }


    private void addTimer()
    {
        this.timer = new Timer(GAME_DELAY, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                snake.move();
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
        while (snake.getHead().equals(food.getPosition()));
    }
}
