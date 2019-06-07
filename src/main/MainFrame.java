package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.OS.AppBouttonListener;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	private Phone phone; 
	public MainFrame()
	{

		//JButton buttonTurn = new JButton("Tourner");
		phone = new Phone();
		
		//
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//
		//add(buttonTurn);
		add(phone, BorderLayout.SOUTH);		

		MainBouttonListener abl = new MainBouttonListener();
		//buttonTurn.addActionListener(abl);

		//
		pack();
		
		
		
	}

	

	public class MainBouttonListener implements ActionListener
	{
		
		@Override
		public void actionPerformed (ActionEvent e)
		{
			// phone.powerOnOff();
		}
	
	} 		
}
