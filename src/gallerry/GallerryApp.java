package gallerry;

import javax.swing.JLabel;

import main.Application;

public class GallerryApp extends Application {
	
	public GallerryApp()
	{
		super("Gallerie", "");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		
		screen.add(titleLabel);		
	}
}