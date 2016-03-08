



import java.awt.Image;

import javax.vecmath.Vector2d;



/**
 * Classe créer pour la gestion des pigeons
 * @author Arnaud
 *
 */
public class Pigeon extends Thread{

	private String name;
	
	private Vector2D position;
	
	private Vector2D vitesse;
	
	private double masse;
	
	private double maxSpeed;
	
	private boolean isMoving;
	
	private int foodEaten;
	
	
	public Pigeon()
	{
		
	}
	


	public Pigeon(String nm, Vector2D pos)
	{
		name = nm;
		position = pos;
		masse = 1;
		maxSpeed = 10;
		isMoving = false;
		foodEaten = 0;
		
	}
	
	public void run()
	{
		
	}
	
	
	public String getPigeonName ()
	{
		return name;
	}
	

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getVitesse() {
		return vitesse;
	}

	public double getMasse() {
		return masse;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
}
