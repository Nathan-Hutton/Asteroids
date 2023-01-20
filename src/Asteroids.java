/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.ArrayList;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 650;
	
	boolean safePeriod = false;
	int lives = 9;
	
	boolean dimming = true;
	boolean brightening = false;
	Color color = new Color(0, 0, 255);

	public static int counter = 0;
	static int counter2 = -110;
	
	private Ship ship;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	private java.util.List<Asteroid> SmallAsteroids = new ArrayList<Asteroid>();
	private Star[] randomStars;
	private java.util.ArrayList<Bullet> deletedBullets = new ArrayList<Bullet>();
	private java.util.ArrayList<Asteroid> collidedAsteroids = new ArrayList<Asteroid>();
	private java.util.ArrayList<Asteroid> collidedSmallAsteroids = new ArrayList<Asteroid>();
	private java.util.ArrayList<Point> bulletPoints = new ArrayList<Point>();
	private java.util.ArrayList<Integer> bulletTimes = new ArrayList<Integer>();
	private java.util.ArrayList<BadShip> BadShips = new ArrayList<BadShip>();
	private java.util.ArrayList<BadShip> collidedBadShips = new ArrayList<BadShip>();
	private boolean bulletNotSet = true;

	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();
		
		randomAsteroids = createRandomAsteroids(5,50,30);
		randomStars = createStars(50,10);
		ship = createShip();
		this.addKeyListener(ship);
		BadShips.add(createBadShip());
	}

	private Ship createShip()
	{
		Point[] shipShape = {
                new Point(0, 0),
                new Point(Ship.SHIP_WIDTH/3.5, Ship.SHIP_HEIGHT/2),
                new Point(0, Ship.SHIP_HEIGHT),
                new Point(Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT/2)
        };
		Point startingPosition = new Point((width -Ship.SHIP_WIDTH)/2, (height - Ship.SHIP_HEIGHT)/2);
        int startingRotation = 0; // Start facing to the right

        return new Ship(shipShape, startingPosition, startingRotation);
	}
	
	private BadShip createBadShip()
	{
		Point[] shipShape = {
                new Point(200, 200),
                new Point(190, 208),
                new Point(180, 210),
                new Point(190, 220),
                new Point(210, 220),
                new Point(220, 210),
                new Point(210, 208)
        };

        return new BadShip(shipShape, new Point (0,0), 0);
	}
	private Asteroid createSmallAsteroid(Point position, int maxAsteroidWidth, int minAsteroidWidth, int number) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			double inRotation = Math.random() * 360;
			Asteroid smallAsteroid = new Asteroid(inSides, position, inRotation, number);
			return smallAsteroid;
	}
	
	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);
	
		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}

	public Star[] createStars(int numberOfStars, int maxRadius) {
	 	Star[] stars = new Star[numberOfStars];
	 	for(int i = 0; i < numberOfStars; ++i) {
	 		Point center = new Point
	(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
 

	 		int radius = (int) (Math.random() * maxRadius);
	 		if(radius < 1) {
	 			radius = 1;
	 		}
	 		stars[i] = new Star(center, radius);
	 	}
	return stars;
	 }

	public void paint(Graphics brush) {
		counter++;
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);
		
		makeStars(brush);
		checkShipState(brush);
		ship.move();
			
		for(BadShip badShip : BadShips)
		{			
			badShip.paint(brush, Color.white);
			if(counter - badShip.getInitialCounter() < 50)
			{
				badShip.initialMovement();
			}
			if(counter - badShip.getInitialCounter() > 100)
			{
				badShip.move();		
			}	
		}
		
		for(Bullet bullet : ship.getBullets())
		{
			bullet.paint(brush, Color.green);
			bullet.move();
			checkBadShipsShot(bullet, brush);
			if(bullet.readyToDelete())
			{
				deletedBullets.add(bullet);
			}
			checkAsteroidsShot(bullet, brush);
			makeSmallerAsteroids(bullet);
		}
		for(BadShip badShip : BadShips)
		{
			for(Bullet bullet : badShip.getBullets())
			{
				if(bulletNotSet)
				{
					double rotation = getRotation(badShip.position, ship.position);
					bullet.setRotation(rotation);
					bulletNotSet = false;
				}
				bullet.paint(brush, Color.red);
				bullet.move();
				if(bullet.readyToDelete())
				{
					deletedBullets.add(bullet);
				}
				if(ship.contains(bullet.center) && !safePeriod)
				{
					counter2 = counter;
					safePeriod = true;
					lives--;
				}
			}
		}
		
		for(int i = 0; i < bulletTimes.size(); i++)
		{
			if(counter - bulletTimes.get(i) <= 4)
			{
				drawLines(brush, bulletPoints.get(i));
			}
			else
			{
				bulletTimes.remove(i);
				bulletPoints.remove(i);
			}
		}
		
		deleteStuff();
		checkEndGame(brush);
	}
	private double getRotation(Point badShipPoint, Point shipPoint) {
		double xDifference = shipPoint.x - badShipPoint.x;
		double yDifference = shipPoint.y - badShipPoint.y;
		double rotation = Math.toDegrees(Math.atan2(yDifference, xDifference));
		if(rotation < 0)
		{
			rotation += 360;
		}
		return rotation;
	}

	public void checkAsteroidsShot(Bullet bullet, Graphics brush)
	{
		for(Asteroid asteroid: randomAsteroids)
		{
			if(asteroid.contains(bullet.getCenter()))
			{	
				bulletPoints.add(new Point (bullet.center.x + 5, bullet.center.y));
				bulletTimes.add(counter);
				collidedAsteroids.add(asteroid);
				deletedBullets.add(bullet);
				
				SmallAsteroids.add(createSmallAsteroid(new Point(asteroid.position.x+20, asteroid.position.y-20),7,10, 1));
				SmallAsteroids.add(createSmallAsteroid(new Point(asteroid.position.x+20, asteroid.position.y+20),7,10, 2));
				SmallAsteroids.add(createSmallAsteroid(new Point(asteroid.position.x-20, asteroid.position.y+20),7,10, 3));
				SmallAsteroids.add(createSmallAsteroid(new Point(asteroid.position.x-20, asteroid.position.y-20),7,10, 4));
			}
		}
	}
	
	public void checkBadShipsShot(Bullet bullet, Graphics brush)
	{
		for(BadShip badShip: BadShips)
		{
			if(badShip.contains(bullet.getCenter()))
			{	
				bulletPoints.add(new Point (bullet.center.x + 5, bullet.center.y));
				bulletTimes.add(counter);
				collidedBadShips.add(badShip);
				deletedBullets.add(bullet);
			}
		}
	}
	
	public void makeSmallerAsteroids(Bullet bullet) {
		for(Asteroid smallAsteroid : SmallAsteroids)
		{
			if(smallAsteroid.contains(bullet.getCenter()))
			{
				bulletTimes.add(counter);
				bulletPoints.add(new Point (bullet.center.x + 5, bullet.center.y));
				collidedSmallAsteroids.add(smallAsteroid);
				deletedBullets.add(bullet);
			}
		}
	}
	
	public void deleteStuff()
	{
		for(Bullet bullet : deletedBullets)
		{
			ship.getBullets().remove(bullet);
			for(BadShip badShip : BadShips)
			{
				badShip.getBullets().remove(bullet);				
				if(badShip.getBullets().size() == 0)
				{
					bulletNotSet = true;				
				}
			}			
		}
		
		for(Asteroid asteroid : collidedAsteroids)
		{
			randomAsteroids.remove(asteroid);
		}
		for(Asteroid smallAsteroid : collidedSmallAsteroids)
		{
			SmallAsteroids.remove(smallAsteroid);
		}
		
		for(BadShip badShip : collidedBadShips)
		{
			BadShips.remove(badShip);
		}
	}

	public void checkEndGame(Graphics brush)
	{
		if(randomAsteroids.isEmpty() && SmallAsteroids.isEmpty() && BadShips.isEmpty())
		{
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.blue);
			brush.drawString("You Win", SCREEN_WIDTH/2 - 25, SCREEN_HEIGHT/2);
		}
		if(lives == 0)
		{
			while(!randomAsteroids.isEmpty())
			{
				randomAsteroids.remove(0);
			}
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.red);
			brush.drawString("You Lost", SCREEN_WIDTH/2 - 25, SCREEN_HEIGHT/2);
		}
	}
	
	public void checkShipState(Graphics brush)
	{
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.white);
			asteroid.move();
			if(asteroid.collision(ship) && !safePeriod)
			{
				counter2 = counter;
				safePeriod = true;
				lives--;
			}
		}
		for(Asteroid smallAsteroid : SmallAsteroids)
		{
			smallAsteroid.paint(brush, Color.white);
			smallAsteroid.move(smallAsteroid.getMovement());
			if(smallAsteroid.collision(ship) && !safePeriod)
			{
				counter2 = counter;
				safePeriod = true;
				lives--;
			}
		}
		if(counter - counter2 < 50)
		{
			ship.paint(brush,Color.red);
		}
		else
		{
			ship.paint(brush,Color.white);	
			safePeriod = false;
		}
	}
	
	public void makeStars(Graphics brush)
	{
		if(dimming)
		{
			color = new Color(0, 0, color.getBlue() - 5);
			if(color.getBlue() < 50)
			{
				dimming = false;
				brightening = true;
			}
		}
		else if(brightening)
		{
			color = new Color(0, 0, color.getBlue() + 5);
			if(color.getBlue() > 250)
			{
				dimming = true;
				brightening = false;
			}
		}
		
		for (Star star : randomStars) {
			star.paint(brush,color);
		}
	}
	
	public void drawLines(Graphics brush, Point position)
	{
		brush.setColor(Color.red);
		Point a1 = new Point(position.x + 5, position.y - 5);
		Point a2 = new Point(position.x + 5, position.y + 5);
		Point a3 = new Point(position.x - 5, position.y + 5);
		Point a4 = new Point(position.x - 5, position.y - 5);
		brush.drawLine((int)position.x, (int)position.y, (int)a1.x, (int)a1.y);
		brush.drawLine((int)position.x, (int)position.y, (int)a2.x, (int)a2.y);
		brush.drawLine((int)position.x, (int)position.y, (int)a3.x, (int)a3.y);
		brush.drawLine((int)position.x, (int)position.y, (int)a4.x, (int)a4.y);
	}
	
	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}