package Entity;

import java.time.LocalTime;

import Math.Vector2D;


/**
 * Classe qui gère la gestion de la nourriture
 * @author Arnaud
 *
 */
public class Food {

	/**
	 * Position de la nourriture
	 */
	private Vector2D position;
	
	/**
	 * Temps de création de la nourriture
	 */
	private LocalTime foodAge;
	
	/**
	 * Boolean pour savoir si la nourriture est moisie ou non.
	 */
	private boolean moisi;

	
	/**
	 * Constructeur de la classe Food
	 * @param pos : Vecteur de la position initiale du pigeon
	 * @param currentTime : Heure de création du pigeon
	 */
	public Food(Vector2D pos, LocalTime currentTime)
	{
		position = pos;
		foodAge = currentTime;
		moisi = false;
	}

	public boolean isMoisi() 
	{
		return moisi;
	}

	public void setMoisi(boolean moisi) 
	{
		this.moisi = moisi;
	}

	public LocalTime getFoodAge()
	{
		return foodAge;
	}
	
	public Vector2D getPosition() 
	{
		return position;
	}	
}
