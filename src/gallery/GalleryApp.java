package gallery;

import javax.swing.JLabel;

import main.Application;

public class GalleryApp extends Application {
	
	public GalleryApp()
	{
		super("Gallerie", "");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		
		screen.add(titleLabel);		
	}
}