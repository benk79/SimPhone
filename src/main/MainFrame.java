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
		
		
		phone = new Phone();
		
		//
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//
		add(phone, BorderLayout.SOUTH);		

		MainBouttonListener abl = new MainBouttonListener();

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
