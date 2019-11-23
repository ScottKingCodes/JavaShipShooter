/**
 * @author Scott King
 *
 *	Uses Polygon to draw the triangle ship
 *
 */

import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Ship extends Sprite {
	
    
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
    	position = new Point2D.Double(615,700);
    	angle = -Math.PI/2;
    	ROTATION = 0.15;
        THRUST = 0.1;
        TOPSPEED =  3;
        active = true;
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
    
}