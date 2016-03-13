public class SteeringBehavior {

	
	
	private Pigeon pigeon;
	
	private Vector2D steeringForce;
	

	public SteeringBehavior(Pigeon pig)
	{
		pigeon = pig;
		steeringForce= new Vector2D();
	}
	
	public Vector2D seek(Vector2D targetPos)
	{
		Vector2D vitesseSouhaite = targetPos.sub(pigeon.getPosition());
		vitesseSouhaite = vitesseSouhaite.normalize();
		//System.out.println("Steering Beha vitesse souhaite = " + vitesseSouhaite);
		vitesseSouhaite = vitesseSouhaite.scale(pigeon.getMaxSpeed());
		//System.out.println("Steering Beha vitesse souhaite = " + vitesseSouhaite);
		vitesseSouhaite = vitesseSouhaite.sub(pigeon.getVitesse());
		return vitesseSouhaite;
		
	}
}
