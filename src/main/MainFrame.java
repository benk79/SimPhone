package main;

import java.awt.BorderLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	
	private JButton buttonTurn = new JButton("Tourner");

	private Phone phone = new Phone();
	 
	public MainFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(buttonTurn);
		add(phone, BorderLayout.SOUTH);		
		
		pack();
	}

}
