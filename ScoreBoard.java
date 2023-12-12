// ScoreBoard.java

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Michael Nguyen
 * @version Last Modified Dec 6th, 2023
 **/
public class ScoreBoard extends JPanel
{
    private int score;


    public ScoreBoard()
    {
        this.score = 0;
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(SnakeGame.WIDTH, 30));
    }


    public int getScore()
    {
        return this.score;
    }


    public void setScore(int score)
    {
        this.score = score;
        this.repaint();
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
    }
}
