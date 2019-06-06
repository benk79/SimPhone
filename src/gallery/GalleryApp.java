package gallery;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import main.Application;

public class GalleryApp extends Application {

	private ArrayList<String> imageList = new ArrayList<String>();
	

	public GalleryApp()	{
		super("Gallerie", "galerie.png");
	}

	public void onInit()
	{
		
		screen.setLayout(new BorderLayout());
		
		// on gère le titre de l'application		
		JLabel title = new JLabel("Gallerie", SwingConstants.CENTER);
		title.setForeground(Color.WHITE);	
		title.setFont(new Font("Arial", Font.PLAIN, 20));
		screen.add(title, BorderLayout.NORTH);
		

		//On ajoute les images à la liste
		imageList.add("ressourcesContenu/Images/baseball.jpg");
		imageList.add("ressourcesContenu/Images/basketball.jpg");
		imageList.add("ressourcesContenu/Images/beach.jpg");
		imageList.add("ressourcesContenu/Images/bmx.jpg");
		imageList.add("ressourcesContenu/Images/hokkaido.jpg");
		
		int size = os.getAppScreenWidth() / 2;
		
		MainView mainView = new MainView(imageList, size);
		
		screen.add(mainView, BorderLayout.CENTER);
	}
	
}