package tictactoe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Phone;
import main.Application;
import main.Config;

public class TicTacToe extends Application
{

	private PanelMain gamePanel = new PanelMain();
	

	public TicTacToe()
	{
		super("Tic Tac Toe", "tictactoe.png");

		//
		screen.setLayout(new BorderLayout());

		//screen.add(new JLabel("<html><h1>" + getName() + "</h1></html>"));

		int phoneWidth = Config.SCREEN_WIDTH;

		gamePanel.setPreferredSize(new Dimension(phoneWidth, phoneWidth));

		screen.add(new JLabel("Morpion"), BorderLayout.NORTH);
		screen.add(gamePanel, BorderLayout.CENTER);
		
		
		

	}


}
