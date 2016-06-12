package Entity;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

import java.net.MalformedURLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import Math.Vector2D;
import PigeonState.SteeringBehavior;



/**
 * Classe cr�er pour la gestion des pigeons
 * @author Arnaud
 *
 */
public class Pigeon {

	/**
	 * String repr�sentant le nom du pigeon
	 */
	private String name;
	
	/**
	 * Vecteur repr�sentant la position du pigeon
	 */
	private Vector2D position;
	
	/**
	 * Vecteur repr�sentant la vitesse du pigeon
	 */
	private Vector2D velocity;
	
	/**
	 * Double repr�sentant la masse du pigeon
	 */
	private double masse;
	
	/**
	 * Double repr�sentant la vitesse maximale du pigeon
	 */
	private double maxSpeed;
	
	/**
	 * Boolean permettant de d�terminer si le pigeon bouge ou s'il est au repos
	 */
	private boolean isMoving;
	
	/**
	 * Int correspondent au nombre de nourriture qu'un pigeon a mang�
	 */
	private int foodEaten;
	
	/**
	 * Instance de la classe Steering behavior contenant les m�thodes de d�placement
	 */
	private SteeringBehavior steering;
	
	/**
	 * Vecteur correspondent � la position de la cible du pigeon
	 */
	private Vector2D targetPos;
	
	/**
	 * double correspondent au temps �coul� entre deux MAJ du jeux
	 */
	private double m_TimeElapsed;
	
	/**
	 * Double correspondent � la distance entre la cible et le pigeon
	 */
	private double distToTarget;
	
	/**
	 * Vecteur correspondent � la distance entre le pigeon et la cible
	 */
    private Vector2D v_distToTarget;
    
    /**
     * Liste de nourriture que le pigeon peut manger
     */
    private ArrayList<Food> listFood;
    
    private int indexFood;
    
    /**
     * Boolean indiquant si le pigeon a peur ou s'il se d�place normalement
     */
    private boolean isPanicking;
    
    /**
     * LocalTime correspondent a une pause de temps entre deux actions de peur
     */
    private LocalTime panickCoolDown;
    
    
    private AudioClip noiseSong;
	
	public Pigeon()
	{
		
	}
	
	/**
	 * Constructeur de la classe Pigeon
	 * @param nm : nom du pigeon
	 * @param pos : position d'origine du pigeon
	 * @param list : liste initiale de nourriture
	 */
	public Pigeon(String nm, Vector2D pos, ArrayList<Food> list)
	{
		name = nm;
		position = pos;
		velocity = new Vector2D();
		masse = 10;
		maxSpeed = 5;
		foodEaten = 0;
		steering = new SteeringBehavior(this);
		listFood = list;
		panickCoolDown = LocalTime.now();
		setIsMoving();
		
		File son = new File("music/gunfire.wav");
		noiseSong = null;
		try
		{
		noiseSong = Applet.newAudioClip(son.toURL());
		}
		catch (MalformedURLException e)
		{
		System.out.println(e.getMessage());
		}
		
	}
	
	
	
	/**
	 * M�thode permettant de savoir si le pigeon est proche de la nourriture ou pas pour la manger.
	 */
	public void eatFood()
	{
		distToTarget = 0;
	    v_distToTarget = new Vector2D();
		v_distToTarget = targetPos.sub(position);
		distToTarget = v_distToTarget.length();
		//System.out.println("Distance to target = " + distToTarget);
		try
		{
			if( indexFood != -1 &&( distToTarget < 30 && listFood.get(indexFood) != null))
			{
				isMoving = false;
				velocity.Reinitialize();
				foodEaten++;
				System.out.println("Le pigeon " + name + " a mang� " + foodEaten);
				for(int i = 0; i<listFood.size(); i++)
				{
					if(targetPos == listFood.get(i).getPosition())
					{
						//System.out.println("La nourriture a bien �t� enlev�");
						listFood.remove(i);
					}
				}
				indexFood = -1;
			}
			panickCoolDown = LocalTime.now();
		}
		catch( Exception e)
		{
			isMoving = false;
			velocity.Reinitialize();
			indexFood = -1;
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode appel� lorsque le pigeon est paniqu� et pour qu'il aille assez loin de la zone de peur
	 */
	public void goPanicking()
	{
		distToTarget = 0;
	    v_distToTarget = new Vector2D();
		v_distToTarget = targetPos.sub(position);
		distToTarget = v_distToTarget.length();
		if( distToTarget < 30)
		{
			isMoving = false;
			velocity.Reinitialize();
			isPanicking = false;
			panickCoolDown = LocalTime.now();
		}
	}
	
	
	/**
	 * M�thode appel� pour mettre � jour les champs du pigeon (vitesse, position) lorsque le pigeon est isMoving
	 * @param timeElapsed : temps entre chaque appel
	 */
	public void update(double timeElapsed)
	{
		m_TimeElapsed = timeElapsed;
		
		Vector2D oldPos = position;
		//System.out.println("timeElaps = " + m_TimeElapsed);
		Vector2D steeringForce = steering.seek(targetPos);
		//System.out.println("force = " + steeringForce);
		
		// Acceleration
		Vector2D acceleration = steeringForce.scale(1/masse);
		//System.out.println("acc = " + acceleration);
		velocity = velocity.add(acceleration.scale(m_TimeElapsed));
		//System.out.println("vit = " + velocity);
		velocity.truncate(maxSpeed);
		//System.out.println("vit = " + velocity);
		position = position.add(velocity.scale(m_TimeElapsed));
		
		//System.out.println(position);
	}
	
	/**
	 * M�thode permettant d'avoir une nourriture fraiche pour le pigeon
	 * @return Food : une nourriture fraiche
	 */
	public Food getFreshest()
	{
		Food freshBread = null;
		LocalTime previousTime = null;
		LocalTime currentTime;
		for( int i = 0; i <listFood.size(); i++)
		{	
			currentTime = listFood.get(i).getFoodAge();
			if( previousTime == null)
			{
				previousTime = currentTime;
				if (listFood.get(i).isMoisi() ==false)
				{
					freshBread = listFood.get(i);
					indexFood = i;
				}
			}
			else if( ChronoUnit.SECONDS.between( previousTime, currentTime) > 0)
			{
				previousTime = currentTime;
				
				if (listFood.get(i).isMoisi() ==false)
				{
					freshBread = listFood.get(i);
					indexFood = i;
				}
			}
		}
		if(freshBread==null)
		{
			System.out.println("aucun pain disponible");
			indexFood = -1;
		}
		
		return freshBread;
	}
	
	/**
	 * Permet de mettre � jour l'�tat du boolean isMoving et isPanicking
	 */
	public void setIsMoving()
	{
		if(listFood.isEmpty())
		{
			if( Math.random() > 0.70 && ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) > 2 && !isPanicking)
			{
				Random randomGen = new Random();
				int x = 10 + randomGen.nextInt(800);
				int y = 10 + randomGen.nextInt(600);
				targetPos = new Vector2D( x,y);
				isMoving = true;
				isPanicking = true;
				noiseSong.play();
			}
			else if( ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) < 2)
			{
				isMoving = false;
				velocity.Reinitialize();
			}
		}
		else
		{
			Food bread = getFreshest();
			if(bread!=null)
			{
				if( Math.random() > 0.70 && ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) > 2 && !isPanicking)
				{
					Random randomGen = new Random();
					int x = 10 + randomGen.nextInt(800);
					int y = 10 + randomGen.nextInt(600);
					targetPos = new Vector2D( x,y);
					isMoving = true;
					isPanicking = true;
					noiseSong.play();
				}
				else if( ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) < 2)
				{
					targetPos = bread.getPosition();
					isMoving = true;
				}
			}
			else
				isMoving = false;	
		}
	}
	
	//Accesseurs
	public String getPigeonName ()
	{
		return name;
	}
	
	public Vector2D getPosition() 
	{
		return position;
	}

	public Vector2D getVitesse() 
	{
		return velocity;
	}

	public double getMasse() 
	{
		return masse;
	}
	
	public double getMaxSpeed() 
	{
		return maxSpeed;
	}
	
	public Vector2D getTargetPos() 
	{
		return targetPos;
	}

	public void setTargetPos(Vector2D targetPos) 
	{
		this.targetPos = targetPos;
	}

	public double getDistToTarget() 
	{
		return distToTarget;
	}

	public ArrayList<Food> getListFood() {
		return listFood;
	}

	public void setListFood(ArrayList<Food> listFood) {
		this.listFood = listFood;
		setIsMoving();
	}

	public boolean isMoving() {
		return isMoving;
	}

	public boolean isPanicking() {
		return isPanicking;
	}
		
	
}
