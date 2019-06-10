package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.OS.AppBouttonListener;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	public MainFrame()
	{
		Phone phone = new Phone();
		
		//
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//
		add(phone, BorderLayout.SOUTH);

		//
		pack();
		
		
		
	}

}
