import java.awt.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Ship extends Polygon implements java.awt.event.KeyListener {
	public static final int SHIP_WIDTH = 80;
	public static final int SHIP_HEIGHT = 18;
	boolean forward = false;
	boolean right = false;
	boolean left = false;
	boolean shoot = false;
	boolean shootStop = true;
	boolean shot = false;
	int[] xCoordinates;
	int[] yCoordinates;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public Ship(Point[] shape, Point position, double rotation) {
		super(shape, position, rotation);
	}

	public void paint(Graphics brush, Color color) {
		Point[] points = getPoints();
		xCoordinates = new int[points.length];
		yCoordinates = new int[points.length];
		
		for(int i = 0; i < points.length; ++i) {
			xCoordinates[i] = (int) points[i].x;
			yCoordinates[i] = (int) points[i].y;
		}
		
		brush.setColor(color);
		brush.drawPolygon(xCoordinates, yCoordinates, 3);
		brush.fillPolygon(xCoordinates, yCoordinates, 3);
	}

	public void move() {
		if(forward)
		{
			position.x += 6 * Math.cos(Math.toRadians(rotation));
			position.y += 6 * Math.sin(Math.toRadians(rotation));
		}
		if(right)
		{
			rotate(6);
		}
		if(left)
		{
			rotate(-6);
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
	
	public ArrayList<Bullet> getBullets()
	{
		return bullets;
	}
	
	public Point getPosition()
	{
		return position;
	}

	public void keyTyped(KeyEvent e)
	{
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !shootStop)
		{
			Bullet bullet = new Bullet(getPoints()[1], rotation);
			bullets.add(bullet);
			shootStop = true;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			shootStop = false;
		}
	}
}
