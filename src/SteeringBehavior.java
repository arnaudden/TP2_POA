import javax.vecmath.Vector2d;





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
		vitesseSouhaite.normalize();
		vitesseSouhaite.scale(pigeon.getMaxSpeed());
		return vitesseSouhaite;
		
	}
}
