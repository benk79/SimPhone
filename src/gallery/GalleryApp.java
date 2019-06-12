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
import contact.ContactApp;
import gallery.ImageView.BlackAndWhiteListener;
import helloworld.HelloWorldApp;
import main.Application;
import main.Config;
import main.SelectionListener;
import main.Serializer;

public class GalleryApp extends Application {


	/**
	 * Card layout to switch between views
	 */
	private CardLayout layout;

	private static final String VIEW_MAIN = "MAIN_VIEW";
	private static final String VIEW_IMAGE = "DETAIL_VIEW";
	private static final String VIEW_CONTACTS = "CONTACT_VIEW";

	private JPanel routerPanel;
	ImageView imageView;
	ListView mainView;

	private ArrayList<GalleryImage> imageList;
	FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");


	private ContactApp contactApp;

	private contact.ListSelectView contactSelectView;

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
		//JLabel title = new JLabel("Gallerie", SwingConstants.CENTER);
		//title.setForeground(Color.WHITE);	
		//title.setBackground(Color.GREEN);
		//title.setFont(new Font("Arial", Font.PLAIN, 20));
		//screen.add(title, BorderLayout.NORTH);
		

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

		mainView = new ListMainView(imageList, size, ibl, abl);
		//mainView.addMenuButton("Ajouter", abl);

		imageView = new ImageView(this, cancelListener);

		screen.add(routerPanel, BorderLayout.CENTER);

		routerPanel.add(mainView, VIEW_MAIN);
		routerPanel.add(imageView, VIEW_IMAGE);

		LoadedAppsListener loadedAppsListener = new LoadedAppsListener();
		os.addLoadedAppsListener(loadedAppsListener);

	}


	/**
	 * Provide ListSelectView as JPanel to other applications to select a contact
	 *
	 * @return JPanel with contact selection options
	 */
	public ListSelectView getSelectContactPanel ()
	{
		ListSelectView selectView = new ListSelectView(imageList);

		return selectView;
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
		setImageViewContactList(img);
		//imageView.setBlackAndWhiteListener(new BlackAndWhiteListener(img, imageView));
		layout.show(routerPanel, VIEW_IMAGE);

	}

	private void setImageViewContactList (GalleryImage galleryImage)
	{
		ArrayList<Contact> contacts = new ArrayList<>();
		//ArrayList<Integer> peopleIds = galleryImage.getPeopleIds();

		for (Integer cId : galleryImage.getPeopleIds()) {
			try {
				Contact c = contactApp.getContact(cId);
				contacts.add(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		imageView.setContactArrayList(contacts);
		//for ()
		//imageView.setContactArrayList();
	}

	private void addImageContact (GalleryImage galleryImage, Contact contact)
	{

		galleryImage.addPeople(contact);

		setImageViewContactList(galleryImage);
	}

	void showImageDetail ()
	{
		layout.show(routerPanel, VIEW_IMAGE);

	}

	private void buildImageList ()
	{
		String dir = "ressourcesContenu/Images";

		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fname = dir + "/" + listOfFiles[i].getName();
				try {
					GalleryImage galleryImage = new GalleryImage(fname);
					imageList.add(galleryImage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	void showContactSelectionPanel ()
	{
		contactSelectView.updateList();
		layout.show(routerPanel, VIEW_CONTACTS);
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

					GalleryImage galleryImage = null;
					try {
						galleryImage = new GalleryImage(newImagePath);
						imageList.add(galleryImage);
					} catch (Exception e) {
						e.printStackTrace();
					}

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


	private class LoadedAppsListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{

			try {
				contactApp = (ContactApp) os.getLoadedApp(ContactApp.class);


				/*
				 * Image select View
				 */

				SelectContactListener selectContactListener = new SelectContactListener();

				contactSelectView = contactApp.getSelectContactPanel();
				contactSelectView.addSelectionListener(selectContactListener);

				routerPanel.add(contactSelectView, VIEW_CONTACTS);
				routerPanel.validate();
				routerPanel.repaint();

				//showView(IMAGE_VIEW);

				System.out.println("contact App linked to gallery");

				imageView.setContactApp(contactApp);

			} catch (ClassNotFoundException e) {
				System.out.println("Could not find ");
			}

		}
	}


	private class SelectContactListener implements SelectionListener
	{

		@Override
		public void onSelect (Object o)
		{
			Contact contact = (Contact) o;

			//imageView.setImage(image);
			//showView(VIEW_IMAGE);

			addImageContact(imageView.getImage(), contact);

			//imageView.updateView();
			showImageDetail();

			/*selectedImage.setText(c.toString());
			screen.remove(selectionPanel);
			screen.validate();
			screen.repaint();*/
		}

		@Override
		public void onCancel ()
		{
			System.out.println("onCancel triggered");
			showImageDetail();
			//showView(DETAIL_VIEW);
			/* screen.remove(selectionPanel);
			screen.validate();
			screen.repaint();*/
		}
	}
	
	
	
}