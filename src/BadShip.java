import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class BadShip extends Polygon{
	private int horizontalMovement;
	private int verticalMovement;
	private boolean move = false;
	boolean shot = false;
	int[] xCoordinates;
	int[] yCoordinates;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int counter;
	private int initialCounter;
	
	public BadShip(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
		counter = Asteroids.counter;
		initialCounter = Asteroids.counter;
	}

	@Override
	public void paint(Graphics brush, Color color) {
		Point[] points = getPoints();
		xCoordinates = new int[points.length];
		yCoordinates = new int[points.length];
		
		for(int i = 0; i < points.length; ++i) {
			xCoordinates[i] = (int) points[i].x;
			yCoordinates[i] = (int) points[i].y;
		}
		
		brush.setColor(color);
		brush.drawPolygon(xCoordinates, yCoordinates, 7);
		brush.drawLine((int)points[2].x, (int)points[2].y, (int)points[5].x, (int)points[5].y);
	}

	@Override
	public void move() {
		checkMovement();
		if(!move)
		{
			return;
		}
		
		position.x += 4 * horizontalMovement;
		position.y += 4 * verticalMovement;
		
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
	
	public void initialMovement()
	{
		position.x += 3;
		position.y += 3;
	}
	
	public void checkDirection()
	{
		Random rand = new Random();
		horizontalMovement = rand.nextInt(2) - 1;
		verticalMovement = rand.nextInt(2) - 1;
		
	}
	
	public void checkMovement()
	{
		if(Asteroids.counter - counter > 50)
		{
			if(move)
			{
				counter = Asteroids.counter;
				move = false;
			}
			else
			{
				counter = Asteroids.counter;
				move = true;
				checkDirection();
				Bullet bullet = new Bullet(getPoints()[0], 0);
				bullets.add(bullet);
				if(horizontalMovement == 0 && verticalMovement == 0)
				{
					checkDirection();
				}
			}
		}
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return bullets;
	}
	
	public int getInitialCounter()
	{
		return initialCounter;
	}

}
