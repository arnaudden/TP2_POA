import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.time.LocalTime;

import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;

/**
 * Classe qui gère la gestion de la nourriture
 * @author Arnaud
 *
 */
public class Food {

	BufferedImage img = null;
	private Vector2d position;
	private LocalTime foodAge;

	
	public Food(Vector2d pos, LocalTime currentTime)
	{
		position = pos;
		foodAge = currentTime;
		try
		{
			URL urlToImage = this.getClass().getResource("/Pictures/Pain.png");
		    img = ImageIO.read( new File( urlToImage.toString()));
		    
		}
		catch (IOException e)
		{
		}
	}
	
	public void draw( Canvas gameCanvas)
	{
	}	
}
