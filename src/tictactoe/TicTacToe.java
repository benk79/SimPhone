package tictactoe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Phone;
import main.Application;

public class TicTacToe extends Application
{

	private JPanel gamePanel = new JPanel(new GridLayout());
	

	public TicTacToe()
	{
		super("Tic Tac Toe", "");

		//
		screen.setLayout(new BorderLayout());

		screen.add(new JLabel("<html><h1>" + getName() + "</h1></html>"));

		int phoneWidth = Phone.PHONE_SCREEN_WIDTH;

		gamePanel.setPreferredSize(new Dimension(phoneWidth, phoneWidth));

		screen.add(new JLabel("Morpion"), BorderLayout.NORTH);
		screen.add(gamePanel, BorderLayout.CENTER);

	}


}
