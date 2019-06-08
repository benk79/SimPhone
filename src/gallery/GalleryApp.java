package gallery;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import contact.Contact;
import main.Application;
import main.Config;
import main.Serializer;

public class GalleryApp extends Application {


	/**
	 * Card layout to switch between views
	 */
	private CardLayout layout;

	private static final String VIEW_MAIN = "MAIN_VIEW";
	private static final String VIEW_IMAGE = "DETAIL_VIEW";

	private JPanel routerPanel;
	ImageView imageView;

	private ArrayList<GalleryImage> imageList;
	

	public GalleryApp()	{
		super("Gallerie", "galerie.png");
		imageList = new ArrayList<GalleryImage>();

		try {
			System.out.println(getDataPath() + "contacts.ser");
			imageList = (ArrayList<GalleryImage>) Serializer.get(getDataPath() + "list.ser");
		} catch (FileNotFoundException e) {
			System.out.println("oops - Gallerie app could not open file list.ser");
			System.out.println("\tThis can happen if there are still no image added.");
			buildImageList();
		} catch (Exception e) {
			System.out.println("oops - Gallerie app could not open file list.ser");
			e.printStackTrace();
		}
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
		/*imageList.add(new GalleryImage("ressourcesContenu/Images/baseball.jpg"));
		imageList.add(new GalleryImage("ressourcesContenu/Images/basketball.jpg"));
		imageList.add(new GalleryImage("ressourcesContenu/Images/beach.jpg"));
		imageList.add(new GalleryImage("ressourcesContenu/Images/bmx.jpg"));
		imageList.add(new GalleryImage("ressourcesContenu/Images/hokkaido.jpg")); */


		layout = new CardLayout();


		routerPanel = new JPanel();
		ImageBouttonListener ibl = new ImageBouttonListener();
		CancelListener cancelListener = new CancelListener();

		routerPanel.setLayout(layout);

		int size = os.getAppScreenWidth() / 2;

		MainView mainView = new MainView(imageList, size, ibl);

		imageView = new ImageView(cancelListener);

		screen.add(routerPanel, BorderLayout.CENTER);

		routerPanel.add(mainView, VIEW_MAIN);
		routerPanel.add(imageView, VIEW_IMAGE);
	}


	/**
	 * Get the path for read/write files and data
	 *
	 * @return The path for read/write data dedicated to this application
	 */
	static String getDataPath ()
	{
		return Config.DATA_PATH + "/gallery/";
	}

	private void showImageDetail (String path)
	{
		imageView.setImage(path);
		layout.show(routerPanel, VIEW_IMAGE);
	}

	private void buildImageList ()
	{
		String dir = "ressourcesContenu/Images";

		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fname = "ressourcesContenu/Images/" + listOfFiles[i].getName();
				GalleryImage galleryImage = new GalleryImage(fname);
				imageList.add(galleryImage);
				System.out.println(galleryImage.getPath());
			}
		}

	}

	/**
	 * Listener for edit contact in list
	 */
	class ImageBouttonListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			ImageButton cb = (ImageButton) actionEvent.getSource();

			showImageDetail(cb.getPath());
		}
	}


	/**
	 * Listener for cancel button in edit form
	 */
	class CancelListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			layout.show(routerPanel, VIEW_MAIN);
		}
	}

}