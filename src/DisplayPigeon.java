import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DisplayPigeon extends JPanel {

	
	
	
	private BufferedImage imagePigeonRest;
	
	private ArrayList<Pigeon> listPigeon;
	
	
	public DisplayPigeon()
	{
		listPigeon = new ArrayList<Pigeon>();
		
	    try
	    {               
	      imagePigeonRest = ImageIO.read(new File("pictures/pigeon_repos.png"));
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
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(listPigeon.isEmpty())
		{
			System.out.println("Liste de pigeon vide");
		}
		else
		{
			for(int i=0; i<listPigeon.size(); i++)
			{
				Pigeon pig = listPigeon.get(i);
				g.drawImage(imagePigeonRest, (int)pig.getPosition().dX, (int)pig.getPosition().dY, null);
			}
		}
		
		repaint();
	}
	
	
}
