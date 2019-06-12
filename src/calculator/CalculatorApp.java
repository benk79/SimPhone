package calculator;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Phone;
import main.Application;
import main.Config;

public class CalculatorApp extends Application
{
	Calculatrice gamePanel = new Calculatrice();
	

	public CalculatorApp()
	{
		super("Calculette", "calcul.png");

		//
		screen.setLayout(new BorderLayout());
		gamePanel.setBackground(Color.BLACK);
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(200, 200));
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		screen.add(label, BorderLayout.NORTH);
		
		int phoneWidth = Config.SCREEN_WIDTH;

		gamePanel.setPreferredSize(new Dimension(phoneWidth, phoneWidth));

		
		screen.add(gamePanel, BorderLayout.CENTER);
		
		
		

	}


}
