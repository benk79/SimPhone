package gallery;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;


import java.io.OutputStream;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import contact.Contact;
import gallery.ImageView.BlackAndWhiteListener;
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
	ListView mainView;

	private ArrayList<GalleryImage> imageList;
	FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");


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
		AddBouttonListener abl = new AddBouttonListener();
		ImageBouttonListener ibl = new ImageBouttonListener();
		CancelListener cancelListener = new CancelListener();
		

		routerPanel.setLayout(layout);

		int size = os.getAppScreenWidth() / 2;

		mainView = new ListMainView (imageList, size, ibl);
		//mainView.addMenuButton("Ajouter", abl);

		imageView = new ImageView(this, cancelListener);

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

	void showImageDetail (GalleryImage img)
	{
		imageView.setImage(img);
		//imageView.setBlackAndWhiteListener(new BlackAndWhiteListener(img, imageView));
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

	public void copyFile (String source, String destination)
		throws IOException
	{

		File copied = new File(destination);
		try (
			InputStream in = new BufferedInputStream(
				new FileInputStream(source));
			OutputStream out = new BufferedOutputStream(
				new FileOutputStream(copied))) {

			byte[] buffer = new byte[1024];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
		}

	}


	/**
	 * Listener for edit image in list
	 */
	class ImageBouttonListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			ImageButton cb = (ImageButton) actionEvent.getSource();

			showImageDetail(cb.getImage());
		}
	}

	/**
	 * Listener for add image in list
	 */
	class AddBouttonListener implements ActionListener
	{

		@Override
		public void actionPerformed (ActionEvent arg0)
		{
			//On instancie un JFileChooser pour pouvoir s?lectionner un fichier
			JFileChooser fileChooser = new JFileChooser();

			//On permet ? l'utilisateur de s?lectionner plusieurs fichier dans la fen?tre
			fileChooser.setMultiSelectionEnabled(true);

			//On applique un filtre sur l'extension du fichier comme d?finit en haut de cette classe
			fileChooser.setFileFilter(filter);

			//Si l'utilisateur valide la selection, on continue
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				/*
				 *On cr?e un tableau d'image avec les fichiers selectionn?s
				 *(On est oblig? d'utiliser un tableau File[] car nous permettons la selection de plusieurs fichiers plus haut dans le code)
				 *Si l'on ne permettait la selection que d'un fichier, File aurait suffit avec la m?thode getSelectedFile() au lieu de getSelectedFileS()
				 */
				File[] selectedImages = fileChooser.getSelectedFiles();

				//On parcours tous les ?l?ments du tableau pr?c?demment cr??
				for (File selectedImage : selectedImages) {

					String loadImagePath = selectedImage.getAbsolutePath();
					String newImageName = Long.toString(System.currentTimeMillis());
					String newImagePath = "ressourcesContenu/Images/" + newImageName + ".jpg";
					try {
						copyFile(loadImagePath, newImagePath);
					} catch (IOException e) {
						e.printStackTrace();
					}

					GalleryImage galleryImage = new GalleryImage(newImagePath);
					imageList.add(galleryImage);
					mainView.updateView();
				}
			}
		}
	}

	void addImage (GalleryImage img)
	{
		imageList.add(img);
		mainView.updateView();
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