/**
 * @author Scott King
 *
 *	Uses Polygon to draw the triangle ship
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Polygon;
import java.awt.Point;


public class Ship extends JComponent {
	
	public Polygon shape;
	public Point position;
	public Point speed;
    
    public Ship()
    {
    	shape = new Polygon();
    	shape.addPoint(615, 700);
    	shape.addPoint(600, 730);
    	shape.addPoint(630, 730);
    	speed = new Point(0,0);
    	position = new Point(0,0);
    }
    
    public void draw(Graphics g)
    {
    	//Draw Ship
        g.setColor(Color.GREEN);
        g.drawPolygon(shape);
    }

    //Moves the ship to the right
    public void moveRight() 
    {
    	this.speed.x =10;
    }
    
    //Moves the ship to the left
    public void moveLeft() 
    {
    	this.speed.x =-10;
    }

    //Moves the ship down
    public void moveDown() 
    {
    	this.speed.y = 10;
    }

    //Moves the ship up
    public void moveUp() 
    {
    	this.speed.y =-10;
    }
    
    public void updatePosition()
    {
    	position.x += speed.x;
    	position.y += speed.y;
    	for (int i = 0; i < shape.npoints; i++)
    	{
    		shape.xpoints[i] += speed.x;
    		shape.ypoints[i] += speed.y;
    	}
    	repaint();
    }
    
    public void paintComponent(Graphics g)
    {
    	
    	//Draw Ship
        g.setColor(Color.GREEN);
        g.drawPolygon(shape);
       
    }
}