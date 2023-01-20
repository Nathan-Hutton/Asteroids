import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle {
	public static final int RADIUS = 8;
	private double rotation;
	private int counter;
	
	public Bullet(Point center, double rotation) {
    	super(center, RADIUS);
    	this.rotation = rotation;
    	counter = Asteroids.counter;
    }

	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int)center.x, (int)center.y, radius, radius);
	}

	public void move() {
		center.x += 12 * Math.cos(Math.toRadians(rotation));
		center.y += 12 * Math.sin(Math.toRadians(rotation));
		if(center.x > Asteroids.SCREEN_WIDTH) {
			center.x -= Asteroids.SCREEN_WIDTH;
		} else if(center.x < 0) {
			center.x += Asteroids.SCREEN_WIDTH;
		}
		
		if(center.y > Asteroids.SCREEN_HEIGHT) {
			center.y -= Asteroids.SCREEN_HEIGHT;
		} else if(center.y < 0) {
			center.y += Asteroids.SCREEN_HEIGHT;
		}
	}
	public boolean readyToDelete()
	{
		if(Asteroids.counter - counter > 49)
		{
			return true;
		}
		return false;
	}
	public Point getCenter()
	{
		return center;
	}
	
	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}

}
