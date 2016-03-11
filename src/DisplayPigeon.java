import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class DisplayPigeon extends JPanel {

	
	private BufferedImage imagePigeonRest;
	private BufferedImage imagePain;
	private BufferedImage imagePainMoisi;
	
	private ArrayList<Pigeon> listPigeon;
	private ArrayList<Food> listFood;
	
	
	public DisplayPigeon()
	{
		listPigeon = new ArrayList<Pigeon>();
		listFood = new ArrayList<Food>();
		
	    try
	    {               
	      imagePigeonRest = ImageIO.read(new File("pictures/pigeon_repos.png"));
	      imagePain = ImageIO.read(new File("pictures/Pain.png"));
	      imagePainMoisi = ImageIO.read( new File("pictures/PainMoisi.png"));
	    }
	    catch (IOException e)
	    {
	      System.out.println("Fichier non trouvé");
	    } 
	    
	}
	
	public void addPigeon(Pigeon pig)
	{
		listPigeon.add(pig);
		repaint();
	}
	
	public void addFood(Food pain)
	{
		listFood.add(pain);
		repaint();
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
				previousTime = currentTime;
			else if( ChronoUnit.SECONDS.between( previousTime, currentTime) > 0)
			{
				previousTime = currentTime;
				freshBread = listFood.get(i);
			}
		}
		
		return freshBread;
	}
	
	public ArrayList<Food> getList()
	{
		return listFood;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(listPigeon.isEmpty())
		{
			
		}
		
		else
		{
			for(int i=0; i<listPigeon.size(); i++)
			{
				Pigeon pig = listPigeon.get(i);
				g.drawImage(imagePigeonRest, (int)pig.getPosition().dX, (int)pig.getPosition().dY, null);
			}
		}
		
		
		if(listFood.isEmpty())
		{}
			//System.out.println("Pas de pain");
		else
		{
			for( int i=0; i<listFood.size(); i++)
			{	
				Food pain = listFood.get(i);	
				if( ChronoUnit.SECONDS.between(pain.getFoodAge(), LocalTime.now()) < 10) // Elapsed Seconds to know if a bread is rotten or not
					g.drawImage( imagePain, (int)pain.getPosition().dX, (int)pain.getPosition().dY, null);
				else
				{
					listFood.get(i).setMoisi(true);
					g.drawImage( imagePainMoisi, (int)pain.getPosition().dX, (int)pain.getPosition().dY, null);
				}
					
			}
		}
		
		repaint();
	}

	public ArrayList<Pigeon> getListPigeon() 
	{
		return listPigeon;
	}
	
	
}
