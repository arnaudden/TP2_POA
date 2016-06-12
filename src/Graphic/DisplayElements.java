package Graphic;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Entity.Food;
import Entity.Pigeon;

/**
 * Classe qui g�re le gamePanel
 * @author Arnaud
 *
 */
public class DisplayElements extends JPanel {

	/**
	 * Image correspondent au pigeon 
	 */
	private BufferedImage imagePigeonRest;
	
	/**
	 * Image correspondent au pain normal
	 */
	private BufferedImage imagePain;
	
	/**
	 * Image correspondent au pain moisi
	 */
	private BufferedImage imagePainMoisi;
	
	/**
	 * Liste de pigeon pr�sent dans la sc�ne
	 */
	private ArrayList<Pigeon> listPigeon;
	
	/**
	 * Liste de nourriture pr�sent dans la sc�ne
	 */
	private ArrayList<Food> listFood;
	
	/**
	 * Constructeur du GamePanel
	 */
	public DisplayElements()
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
	      System.out.println("Fichier non trouv�");
	    } 
	    
	}
	
	/**
	 * M�thode appel� lors de l'ajout d'un pigeon dans la sc�ne
	 * @param pig : Pigeon � ajouter
	 */
	public void addPigeon(Pigeon pig)
	{
		
		listPigeon.add(pig);
		repaint();
	}
	
	/**
	 * m�thode appel� lors de l'ajout d'une nourriture
	 * @param bread : nourriture � ajouter
	 */
	public void addFood(Food bread)
	{
		listFood.add(bread);
		for(int i=0; i<listPigeon.size(); i++)
		{
			listPigeon.get(i).setListFood(listFood);
		}
		repaint();
	}
	
	public ArrayList<Food> getListFood()
	{
		return listFood;
	}
	
	/**
	 * Appel� pour mettre � jour le GamePanel (d�placement des pigeons
	 * et apparition/disparition de la nourriture
	 */
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
				g.drawImage(imagePigeonRest, (int)pig.getPosition().getX(), (int)pig.getPosition().getY(), null);
				
			}
		}
		
		
		if(listFood.isEmpty())
		{
			for (int i=0; i<listPigeon.size(); i++)
			{
				listPigeon.get(i).setIsMoving();
			}
		}
			//System.out.println("Pas de pain");
		else
		{
			for( int i=0; i<listFood.size(); i++)
			{	
				Food pain = listFood.get(i);	
				if( ChronoUnit.SECONDS.between(pain.getFoodAge(), LocalTime.now()) < 10) // Elapsed Seconds to know if a bread is rotten or not
					g.drawImage( imagePain, (int)pain.getPosition().getX(), (int)pain.getPosition().getY(), null);
				else
				{
					listFood.get(i).setMoisi(true);
					g.drawImage( imagePainMoisi, (int)pain.getPosition().getX(), (int)pain.getPosition().getY(), null);
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
