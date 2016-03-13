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



public class GameGraphique implements MouseListener {



	private JFrame gameWindow;
	
	private DisplayPigeon gamePanel;
	
	private JPanel menuPanel;
	
	private JLabel namePigeonLabel;
	
	private JTextField namePigeonTF;
	
	private JButton addPigeonButton;
	
	private Font fontPrincipal;
	
	
	
	public GameGraphique()
	{
		

		fontPrincipal = new Font("Arial", java.awt.Font.PLAIN, 16);
		
		gameWindow = new JFrame("Pigeon Game");
		gameWindow.setSize(1000,1000);
		
		// Gestion du GamePanel et GameCanvas
		gamePanel = new DisplayPigeon();
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
	
	
	public void addPigeonOnScreen()
	{
		System.out.println("Clique sur add Pigeon");
		
		Random randomGen = new Random();
		int x = 10 + randomGen.nextInt(1000);
		int y = 10 + randomGen.nextInt(800);
		
		System.out.println("test 1" + x  + " "+ y);
		
		Pigeon pigeon = new Pigeon(namePigeonTF.getText(), new Vector2D(x, y), gamePanel.getListFood());
		pigeon.start();
		gamePanel.addPigeon(pigeon);
		
	}
	
	public void addFoodOnScreen( int x, int y)
	{
		System.out.println(x + " " + y);
	/*	Vector2D posNew = new Vector2D(x-35,y-35);
		Vector2D posNeutral = posNew;
		ArrayList<Food> listF= gamePanel.getList();
		if( listF.size() != 0)
		{
			for( int i = 0; i < listF.size(); i++)
			{
				if(  posNew.sub(listF.get(i).getPosition()).length() > 50)
				{
					Food food = new Food( posNeutral, LocalTime.now());
					gamePanel.addFood( food);
				}
				posNew = posNeutral;
			}
		}
		else
		{*/
			Food food = new Food( new Vector2D( x-35, y-35), LocalTime.now());
			gamePanel.addFood( food);
		//}
			
	}
	
}
