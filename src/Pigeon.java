import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



/**
 * Classe créer pour la gestion des pigeons
 * @author Arnaud
 *
 */
public class Pigeon extends Thread{

	/**
	 * String représentant le nom du pigeon
	 */
	private String name;
	
	/**
	 * Vecteur représentant la position du pigeon
	 */
	private Vector2D position;
	
	/**
	 * Vecteur représentant la vitesse du pigeon
	 */
	private Vector2D velocity;
	
	/**
	 * Double représentant la masse du pigeon
	 */
	private double masse;
	
	/**
	 * Double représentant la vitesse maximale du pigeon
	 */
	private double maxSpeed;
	
	/**
	 * Boolean permettant de déterminer si le pigeon bouge ou s'il est au repos
	 */
	private boolean isMoving;
	
	/**
	 * Int correspondant au nombre de nourriture qu'un pigeon a mangé
	 */
	private int foodEaten;
	
	/**
	 * Instance de la classe Steering behavior contenant les méthodes de déplacement
	 */
	private SteeringBehavior steering;
	
	/**
	 * Vecteur correspondant à la position de la cible du pigeon
	 */
	private Vector2D targetPos;
	
	/**
	 * double correspondant au temps écoulé entre deux MAJ du jeux
	 */
	private double m_TimeElapsed;
	
	/**
	 * Double correspondant à la distance entre la cible et le pigeon
	 */
	private double distToTarget;
	
	/**
	 * Vecteur correspondant à la distance entre le pigeon et la cible
	 */
    private Vector2D v_distToTarget;
    
    /**
     * Liste de nourriture que le pigeon peut manger
     */
    private ArrayList<Food> listFood;
    
    private int indexFood;
    
    /**
     * Boolean indiquant si le pigeon a peur ou s'il se déplace normalement
     */
    private boolean isPanicking;
    
    /**
     * LocalTime correspondant a une pause de temps entre deux actions de peur
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
	 * Méthode appelé automatiquement car la Classe Pigeon est un Thread. Cette méthode met à jour l'état
	 * du pigeon pour savoir s'il doit aller manger une nourriture ou rester au repos
	 */
	public void run()
	{
	
		 long lastTimeFPS = System.currentTimeMillis();
	     long lastTime = System.nanoTime();
	     double unprocessed = 0;
	        
	     double framerate = 60;
	     double nsPerTick = 1000000000.0 / framerate;
	     boolean shouldRender = false;
	     double nbTick=0;
	     
	     
		
		while(true)
		{
			shouldRender = false;
        	unprocessed+=(System.nanoTime() - lastTime) / nsPerTick;
        	lastTime = System.nanoTime();
        	
        	for(double i=1; i<unprocessed;i++ )
        	{
        		unprocessed--;
        		nbTick++;
        		
        	}
        	
        	double timeFPS =  System.currentTimeMillis() - lastTimeFPS;
        	if (timeFPS > 10)
        	{
        		shouldRender=true;
        		//System.out.println("PigeonGame FPS : " + nbTick);
        		lastTimeFPS = System.currentTimeMillis();
        		nbTick = 0;
        	}
        	
        	if (shouldRender)
        	{
        		if (!isMoving)
    			{
        			setIsMoving();
    				//System.out.println("Le pigeon " + name + " est au repos");
    			}
    			else if (isMoving)
    			{
    				setIsMoving();
    				if( !isPanicking)
    				{
    					double time = timeFPS/10;
    					update(time);
    					eatFood();
    				}
    				else if( isPanicking)
    				{
    					double time = timeFPS/10;
    					update(time);
    					goPanicking();
    				}
    			}
        	}
			
			
			// Delay processing
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		
	}
	
	/**
	 * Méthode permettant de savoir si le pigeon est proche de la nourriture ou pas pour la manger.
	 */
	synchronized public void eatFood()
	{
		distToTarget = 0;
	    v_distToTarget = new Vector2D();
		v_distToTarget = targetPos.sub(position);
		distToTarget = v_distToTarget.length();
		//System.out.println("Distance to target = " + distToTarget);
		try
		{
			if(distToTarget < 30 && listFood.get(indexFood) != null)
			{
				isMoving = false;
				velocity.Reinitialize();
				foodEaten++;
				System.out.println("Le pigeon " + name + " a mangé " + foodEaten);
				for(int i = 0; i<listFood.size(); i++)
				{
					if(targetPos == listFood.get(i).getPosition())
					{
						//System.out.println("La nourriture a bien été enlevé");
						listFood.remove(i);
					}
				}
			}
			panickCoolDown = LocalTime.now();
		}
		catch( Exception e)
		{
			e.printStackTrace();
			isMoving = false;
			velocity.Reinitialize();
		}
	}
	
	/**
	 * Méthode appelé lorsque le pigeon est paniqué et pour qu'il aille assez loin de la zone de peur
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
	 * Méthode appelé pour mettre à jour les champs du pigeon (vitesse, position) lorsque le pigeon est isMoving
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
	 * Méthode permettant d'avoir une nourriture fraiche pour le pigeon
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
					freshBread = listFood.get(i);
				
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
		}
		
		return freshBread;
	}
	
	/**
	 * Permet de mettre à jour l'état du boolean isMoving et isPanicking
	 */
	public void setIsMoving()
	{
		if(listFood.isEmpty())
		{
			if( Math.random() > 0.70 && ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) > 2 && !isPanicking)
			{
				Random randomGen = new Random();
				int x = 10 + randomGen.nextInt(1000);
				int y = 10 + randomGen.nextInt(800);
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
					int x = 10 + randomGen.nextInt(1000);
					int y = 10 + randomGen.nextInt(800);
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
		
}
