import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Vector2d;



/**
 * Classe créer pour la gestion des pigeons
 * @author Arnaud
 *
 */
public class Pigeon extends Thread{

	private String name;
	
	private Vector2D position;
	
	private Vector2D velocity;
	
	private double masse;
	
	private double maxSpeed;
	
	private boolean isMoving;
	
	private int foodEaten;
	
	private SteeringBehavior steering;
	
	private Vector2D targetPos;
	
	private double m_TimeElapsed;
	
	private double distToTarget;
    private Vector2D v_distToTarget;
    
    private ArrayList<Food> listFood;
    
    private boolean isPanicking;
    private LocalTime panickCoolDown;
	
	public Pigeon()
	{
		
	}
	
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
	}
	
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
			 // TP1 : Gestion des ticks
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
	
	public void eatFood()
	{
		distToTarget = 0;
	    v_distToTarget = new Vector2D();
		v_distToTarget = targetPos.sub(position);
		distToTarget = v_distToTarget.length();
		//System.out.println("Distance to target = " + distToTarget);
		if(distToTarget < 30)
		{
			isMoving = false;
			velocity.Reinitialize();
			foodEaten++;
			System.out.println("Le pigeon " + name + " a mangé " + foodEaten);
			for(int i = 0; i<listFood.size(); i++)
			{
				if(targetPos ==listFood.get(i).getPosition())
				{
					listFood.remove(i);
					//System.out.println("La nourriture a bien été enlevé");
				}
			}
			panickCoolDown = LocalTime.now();
		}
	}
	
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
					freshBread = listFood.get(i);
			}
		}
		if(freshBread==null)
		{
			System.out.println("aucun pain disponible");
		}
		
		return freshBread;
	}
	
	public void setIsMoving()
	{
		if(listFood.isEmpty())
		{
			if( Math.random() > 0.70 && ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) > 6 && !isPanicking)
			{
				targetPos = new Vector2D( 1000 - position.dX, 800 - position.dY);
				isMoving = true;
				isPanicking = true;
			}
			else if( ChronoUnit.SECONDS.between( panickCoolDown, LocalTime.now()) < 6)
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
				System.out.println( "true");
				targetPos = bread.getPosition();
				isMoving = true;
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
