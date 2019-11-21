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
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.Point;


public class Ship extends JComponent {
	
	public Polygon shape;
	public Polygon drawShape;
	public Point position;
	public Point2D.Double speed;
	public double angle;
	double ROTATION;
    double THRUST;
    double TOPSPEED;
	
    
    public Ship()
    {
    	shape = new Polygon();
    	shape.addPoint(15, 0);
    	shape.addPoint(-10, 10);
    	shape.addPoint(-10, -10);
    	drawShape = new Polygon();
    	drawShape.addPoint(15, 0);
    	drawShape.addPoint(-10, 10);
    	drawShape.addPoint(-10, -10);
    	speed = new Point2D.Double(0,0);
    	position = new Point(615,700);
    	angle = -Math.PI/2;
    	ROTATION = 0.15;
        THRUST = 0.1;
        TOPSPEED =  3;
    }
    
    public void draw(Graphics g)
    {
    	//Draw Ship
        g.setColor(Color.GREEN);
        g.drawPolygon(drawShape);
    }
    
    public void accelerate()
    {
    	Point2D.Double nextStep = new Point2D.Double(0,0);
    	nextStep.x = speed.x + Math.cos(angle)*THRUST;
    	nextStep.y = speed.y + Math.sin(angle)*THRUST;
    	
    	if (Math.abs(nextStep.x) < TOPSPEED && Math.abs(nextStep.y) < TOPSPEED)
    	{
    		speed.x += Math.cos(angle)*THRUST;
    		speed.y += Math.sin(angle)*THRUST;
    	}
    }
    
    public void deccelarate() 
    {

    }
    
    public void rotateLeft()
    {
    	this.angle -= ROTATION;
    }
    
    public void rotateRight()
    {
    	this.angle += ROTATION;
    }
    
    public void updatePosition()
    {
    	int x, y;
    	
    	wraparound();
    	
    	position.x += speed.x;
    	position.y += speed.y;
    	
    	for (int i = 0; i < shape.npoints; i++)
    	{
    		x = (int)Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
    		y = (int)Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));
    		
    		drawShape.xpoints[i] = x;
    		drawShape.ypoints[i] = y;
    		
    	}
    	 drawShape.invalidate();
         drawShape.translate((int)Math.round(position.x), (int)Math.round(position.y));
    	repaint();
    }
    
    private void wraparound()
    {
        if (position.x > 1200)
        {
            position.x = 0;
        }
        
        if (position.x < 0)
        {
        	position.x = 1200;
        }
        
        if (position.y > 800)
        {
        	position.y = 0;
        }
        
        if (position.y < 0)
        {
        	position.y = 600;
        }
    }
    
    public void paintComponent(Graphics g)
    {
    	
    	//Draw Ship
        g.setColor(Color.GREEN);
        g.drawPolygon(drawShape);
       
    }
}