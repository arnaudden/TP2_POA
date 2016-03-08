import java.awt.image.BufferedImage;
import java.time.LocalTime;


/**
 * Classe qui gère la gestion de la nourriture
 * @author Arnaud
 *
 */
public class Food {

	BufferedImage img = null;
	private Vector2D position;
	private LocalTime foodAge;

	
	public Food(Vector2D pos, LocalTime currentTime)
	{
		position = pos;
		foodAge = currentTime;
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
