import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Vector2d;



public class GameGraphique implements MouseListener {



	private JFrame gameWindow;
	
	private JPanel gamePanel;
	
	private Canvas gameCanvas;
	
	private JPanel menuPanel;
	
	private JLabel namePigeonLabel;
	
	private JTextField namePigeonTF;
	
	private JButton addPigeonButton;
	
	private Font fontPrincipal;
	
	private ArrayList<Pigeon> listPigeon;
	
	private ArrayList<Food> listFood;
	
	public GameGraphique()
	{
		fontPrincipal = new Font("Arial", java.awt.Font.PLAIN, 16);
		
		gameWindow = new JFrame("Pigeon Game");
		gameWindow.setSize(1000,1000);
		
		// Gestion du GamePanel et GameCanvas
		gamePanel = new JPanel(new BorderLayout());
		gamePanel.setSize(1000,800);
		gameCanvas = new Canvas();
		gameCanvas.addMouseListener(this);
		gamePanel.add(gameCanvas);
		
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
		gameWindow.setVisible(true);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == addPigeonButton)
		{
			addPigeonOnScreen();
		}
		
		if(e.getSource() == gameCanvas)
		{
			addFoodOnScreen( e.getX(), e.getY());
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
		int x = 10 + (int)(Math.random() * ((100 - 10) + 1));
		int y = 10 + (int)(Math.random() * ((100 - 10) + 1));
		
		Pigeon pigeon = new Pigeon( namePigeonTF.getText(), new Vector2D( x, y), new Vector2D( 0, 0));
	}
	
	public void addFoodOnScreen( int x, int y)
	
	{
		Food newPain = new Food( new Vector2d( x, y), LocalTime.now());
		newPain.draw( gameCanvas);
		listFood.add( newPain);
	}
}
