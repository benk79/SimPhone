package calendar;

import javax.swing.JLabel;

import main.Application;

public class calendarApp extends Application {
	
	public calendarApp()
	{
		super("Calendrier");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		
		screen.add(titleLabel);
	}

}
