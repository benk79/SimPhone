package contact;

import javax.swing.JLabel;

import main.Application;

public class ContactApp extends Application {
	
	public ContactApp()
	{
		super("Contacts", "");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		
		screen.add(titleLabel);		
	}
}
