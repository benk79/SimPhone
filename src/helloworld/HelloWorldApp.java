package helloworld;

import javax.swing.JLabel;

import main.Application;

public class HelloWorldApp extends Application {
	
	public HelloWorldApp()
	{
		super("HelloWorld");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		
		screen.add(titleLabel);		
	}

}
