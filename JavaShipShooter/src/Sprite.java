/**
 * 
 */

/**
 * @author Scott King
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Sprite extends JComponent {

	public Polygon shape;
	public Polygon drawShape;
	public Point2D.Double position;
	public Point2D.Double speed;
	public double angle;
	double ROTATION;
    double THRUST;
    double TOPSPEED;
    boolean active;
    int counter;
	
    
    public void updatePosition()
    {
    	int x, y;
    	
    	wraparound();
    	counter++;
    	
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
    
    public void wraparound()
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
        	position.y = 800;
        }
    }
    
    public void paintComponent(Graphics g)
    {    	
    	//Draw Sprite
        g.setColor(Color.GREEN);
        g.drawPolygon(drawShape);
       
    }
}
