
import java.time.LocalTime;


/**
 * Classe qui gère la gestion de la nourriture
 * @author Arnaud
 *
 */
public class Food {

	private Vector2D position;
	private LocalTime foodAge;
	private boolean moisi;

	
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
