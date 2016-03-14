
public class PigeonState implements Runnable{

	private Pigeon pigeon;
	
	public PigeonState(Pigeon pig)
	{
		pigeon = pig;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
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
       		if (!pigeon.isMoving())
   			{
       			pigeon.setIsMoving();
   				//System.out.println("Le pigeon " + name + " est au repos");
   			}
   			else if (pigeon.isMoving())
   			{
   				pigeon.setIsMoving();
   				if( !pigeon.isPanicking())
   				{
   					double time = timeFPS/10;
   					pigeon.update(time);
   					pigeon.eatFood();
   				}
   				else if( pigeon.isPanicking())
   				{
   					double time = timeFPS/10;
   					pigeon.update(time);
   					pigeon.goPanicking();
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

	
	
}
