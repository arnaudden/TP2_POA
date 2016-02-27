import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



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
			addFoodOnScreen();
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
	}
	
	public void addFoodOnScreen()
	
	{
		System.out.println("Clique n'importe où");
	}
}
