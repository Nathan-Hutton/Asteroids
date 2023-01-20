import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Asteroid extends Polygon {	
	private int movement;
	
	public Asteroid(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
	}
	
	public Asteroid(Point[] shape, Point position, double rotation, int movement) {
		super(shape, position, rotation);
		this.movement = movement;
	}
	
	@Override
	public void paint(Graphics brush, Color color) {
		Point[] points = getPoints();
		int[] xCoordinates = new int[points.length];
		int[] yCoordinates = new int [points.length];
		
		for(int i = 0; i < points.length; i++)
		{
			xCoordinates[i] = (int)points[i].x;
			yCoordinates[i] = (int)points[i].y;
		}
		brush.setColor(color);
		brush.drawPolygon(xCoordinates, yCoordinates, points.length);
	}

	@Override
	public void move() {
		position.x += 5;
		position.y += 5;
		if(position.x > Asteroids.SCREEN_WIDTH) {
			position.x -= Asteroids.SCREEN_WIDTH;
		} else if(position.x < 0) {
			position.x += Asteroids.SCREEN_WIDTH;
		}
		
		if(position.y > Asteroids.SCREEN_HEIGHT) {
			position.y -= Asteroids.SCREEN_HEIGHT;
		} else if(position.y < 0) {
			position.y += Asteroids.SCREEN_HEIGHT;
		}
	}
	
	public void move(int movement) {
		if(movement == 1)
		{
			position.x += 5;
			position.y -= 5;			
		}
		if(movement == 2)
		{
			position.x += 5;
			position.y += 5;			
		}
		if(movement == 3)
		{
			position.x -= 5;
			position.y += 5;			
		}
		if(movement == 4)
		{
			position.x -= 5;
			position.y -= 5;			
		}
		if(position.x > Asteroids.SCREEN_WIDTH) {
			position.x -= Asteroids.SCREEN_WIDTH;
		} else if(position.x < 0) {
			position.x += Asteroids.SCREEN_WIDTH;
		}
		
		if(position.y > Asteroids.SCREEN_HEIGHT) {
			position.y -= Asteroids.SCREEN_HEIGHT;
		} else if(position.y < 0) {
			position.y += Asteroids.SCREEN_HEIGHT;
		}
	}
	
	public void setMovement(int movement)
	{
		this.movement = movement;
	}
	public int getMovement()
	{
		return movement;
	}
}
