package main;

import java.awt.BorderLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	 
	public MainFrame()
	{
		
		JButton buttonTurn = new JButton("Tourner");
		Phone phone = new Phone();
		
		//
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//
		add(buttonTurn);
		add(phone, BorderLayout.SOUTH);		
		
		//
		pack();
	}

}
