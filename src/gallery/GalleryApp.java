package gallery;

import java.awt.*;
import javax.swing.*;

import main.Application;

public class GalleryApp extends Application {
	
	
	private ViewImages listImages;

	
	public GalleryApp()
	{
		super("Gallerie", "galerie.png");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		screen.add(titleLabel);	
		
		
	}
	
	public void onInit()
	{
		JButton addButton = new JButton("Ajouter");
		
		screen.setLayout(new BorderLayout());
		listImages.setBackground(Color.BLUE);
		
		screen.add(listImages);
		screen.add(addButton, BorderLayout.SOUTH);
		
	}
	

}