package calendar;

import javax.swing.JLabel;

import main.Application;

public class calendarApp extends Application {
	
	public calendarApp()
	{
		super("Calendrier", "C:/Users/Louise/git/SimPhone/ressourcesSystem/calendrier.png");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		
		screen.add(titleLabel);
	}

}
