import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Classe qui g�re la fenetre principale du jeu
 * @author Arnaud
 *
 */
public class GameGraphique implements MouseListener {



	/**
	 * JFrame correspondant � la fen�tre principal
	 */
	private JFrame gameWindow;
	
	/**
	 * Panel contenant la zone public avec les pigeons et la nourriture
	 */
	private DisplayElements gamePanel;
	
	/**
	 * JPanel contenant le menu pour ajouter un pigeon
	 */
	private JPanel menuPanel;
	
	/**
	 * Label contenant une phrase "Nom du pigeon : "
	 */
	private JLabel namePigeonLabel;
	/**
	 * TextField permettant de r�cup�rer le nom du pigeon
	 */
	private JTextField namePigeonTF;
	
	/**
	 * Bouton pour ajouter un pigeon
	 */
	private JButton addPigeonButton;

	/**
	 * Font pour avoir les textes en gros caract�re
	 */
	private Font fontPrincipal;
	
	
	/**
	 * Constructeur de la classe GameGraphique : permet de placer chaque panel 
	 * au lancement de l'application
	 */
	public GameGraphique()
	{
		

		fontPrincipal = new Font("Arial", java.awt.Font.PLAIN, 16);
		
		gameWindow = new JFrame("Pigeon Game");
		gameWindow.setSize(1000,1000);
		
		// Gestion du GamePanel et GameCanvas
		gamePanel = new DisplayElements();
		gamePanel.setSize(1000,800);
		gamePanel.addMouseListener(this);
		
		// gestion du MenuPanel
		
		menuPanel = new JPanel(new FlowLayout());
		menuPanel.setSize(1000, 200);
		menuPanel.setBackground(Color.GRAY);
		
		addPigeonButton = new JButton("Ajouter un pigeon");
		addPigeonButton.addMouseListener(this);
		addPigeonButton.setFont(fontPrincipal);
		
		namePigeonLabel = new JLabel("Pigeon name : ");
		namePigeonLabel.setFont(fontPrincipal);
		
		namePigeonTF = new JTextField("Enter your pigeon name");
		namePigeonTF.setFont(fontPrincipal);

		menuPanel.add(namePigeonLabel);
		menuPanel.add(namePigeonTF);
		menuPanel.add(addPigeonButton);
		
		gameWindow.add(menuPanel, BorderLayout.NORTH);
		gameWindow.add(gamePanel);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setResizable(false);
		gameWindow.setVisible(true);
		
	}
	

	/**
	 * M�thode qui permet de d�tecter le clique de la sourie
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addPigeonButton)
		{
			addPigeonOnScreen();
		}
		
		if(e.getSource() == gamePanel)
		{
			addFoodOnScreen(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * M�thode permettant d'ajouter un pigeon � l'�cran
	 */
	public void addPigeonOnScreen()
	{		
		Random randomGen = new Random();
		int x = 10 + randomGen.nextInt(1000);
		int y = 10 + randomGen.nextInt(800);

		Vector2D posNew = new Vector2D( x - 37, y - 30);
		Vector2D posNeutral = posNew;
		ArrayList<Food> listPigeon = gamePanel.getListFood();
		boolean spawnPossible = false;
		try
		{
			if( namePigeonTF.getText().isEmpty() || namePigeonTF.getText().contains(" "))
				throw new InputPigeonException();
			
			if( listPigeon.size() != 0)
			{
				for( int i = 0; i < listPigeon.size(); i++)
				{
					try
					{
						if( namePigeonTF.getText().equals(listPigeon.get((i))))
								throw new InputPigeonException( namePigeonTF.getText());
						
						if(  posNew.sub(listPigeon.get(i).getPosition()).length() < 50)
						{
							addPigeonOnScreen();
							break;
						}
						else
						{
							spawnPossible = true;
						}
						posNew = posNeutral;
						}
					catch( InputPigeonException e)
					{
						e.printStackTrace();
					}
				}
				
				
				if( spawnPossible)
				{
					Pigeon pigeon = new Pigeon(namePigeonTF.getText(), new Vector2D(x - 37, y-30), gamePanel.getListFood());
					pigeon.start();
					gamePanel.addPigeon(pigeon);
				}	
			}
			else // Si liste pigeons vide
			{


						Pigeon pigeon = new Pigeon(namePigeonTF.getText(), new Vector2D(x - 37, y-30), gamePanel.getListFood());
						pigeon.start();
						gamePanel.addPigeon(pigeon);

			}
	
		}
		catch( InputPigeonException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode permettant d'ajouter une nourriture
	 * @param x : position x de la nourriture
	 * @param y : position y de la nourriture
	 */
	public void addFoodOnScreen( int x, int y)
	{
		Vector2D posNew = new Vector2D(x-35,y-35);
		Vector2D posNeutral = posNew;
		ArrayList<Food> listF= gamePanel.getListFood();
		boolean okToDraw = false;
		if( listF.size() != 0)
		{
			for( int i = 0; i < listF.size(); i++)
			{
				if(  posNew.sub(listF.get(i).getPosition()).length() < 50)
				{
					okToDraw = false;
					break;
				}
				else
				{
					okToDraw = true;
				}
				posNew = posNeutral;
			}
			if( okToDraw)
			{
				Food food = new Food( posNeutral, LocalTime.now());
				gamePanel.addFood( food);
			}	
		}
		else
		{
			Food food = new Food( new Vector2D( x-35, y-35), LocalTime.now());
			gamePanel.addFood( food);
		}	
	}
	
}
