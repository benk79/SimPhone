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

public class GalleryApp extends Application
{

	/**
	 * Card layout to switch between views
	 */
	private CardLayout layout;


	/**
	 * Main view name used by card layout
	 */
	private static final String VIEW_MAIN = "MAIN_VIEW";

	/**
	 * Image view name used by card layout
	 */
	private static final String VIEW_IMAGE = "DETAIL_VIEW";

	/**
	 * Contact selection view name used by card layout
	 */
	private static final String VIEW_CONTACTS = "CONTACT_VIEW";

	/**
	 * Panel used with card layout to navigate between views
	 */
	private JPanel routerPanel;

	/**
	 * Image view
	 */
	private ImageView imageView;

	/**
	 * List view
	 */
	private ListView mainView;

	/**
	 * Contact selection view
	 */
	private contact.ListSelectView contactSelectView;

	/**
	 * List of images
	 */
	private ArrayList<GalleryImage> imageList;

	/**
	 * Filter to select image type files
	 */
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");

	/**
	 * Contact application to be linked
	 */
	private ContactApp contactApp;


	/**
	 * Constructor of the class
	 */
	public GalleryApp ()
	{
		super("Gallerie", "galerie.png");
		imageList = new ArrayList<GalleryImage>();

		try {
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


	/**
	 * Save data of imageList to file
	 */
	void writeFile ()
	{
		try {
			Serializer.set(getDataPath() + "list.ser", imageList);
		} catch (Exception e) {
			System.out.println("oops");
			e.printStackTrace();
		}
	}


	/**
	 * Called by os when Application.os has been set
	 */
	public void onInit ()
	{
		screen.setLayout(new BorderLayout());
		layout = new CardLayout();

		routerPanel = new JPanel();
		routerPanel.setLayout(layout);
		screen.add(routerPanel, BorderLayout.CENTER);

		//
		ImageBouttonListener ibl = new ImageBouttonListener();
		AddBouttonListener abl = new AddBouttonListener();
		mainView = new ListMainView(imageList, ibl, abl);

		//
		CancelListener cancelListener = new CancelListener();
		imageView = new ImageView(this, cancelListener);

		//
		routerPanel.add(mainView, VIEW_MAIN);
		routerPanel.add(imageView, VIEW_IMAGE);

		//
		LoadedAppsListener loadedAppsListener = new LoadedAppsListener();
		os.addLoadedAppsListener(loadedAppsListener);
	}


	/**
	 * Provide ListSelectView as JPanel to other applications to select an image
	 *
	 * @return JPanel with contact selection options
	 */
	public ListSelectView getSelectContactPanel ()
	{
		return new ListSelectView(imageList);
	}


	/**
	 * Get the path for read/write files and data
	 *
	 * @return The path for read/write data dedicated to this application
	 */
	private String getDataPath ()
	{
		return Config.DATA_PATH + "/gallery/";
	}


	/**
	 * Show the image view (and set image)
	 *
	 * @param img Image to show in the view
	 */
	void showImageDetail (GalleryImage img)
	{
		imageView.setImage(img);
		setImageViewContactList(img);
		layout.show(routerPanel, VIEW_IMAGE);
	}


	/**
	 * Set the image of the image view
	 *
	 * @param galleryImage Image to set in the view
	 */
	void setImageViewContactList (GalleryImage galleryImage)
	{
		ArrayList<Contact> contacts = new ArrayList<>();

		for (Integer cId : galleryImage.getPeopleIds()) {
			try {
				Contact c = contactApp.getContact(cId);
				contacts.add(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		imageView.setContactArrayList(contacts);
	}


	/**
	 * Tag a contact for an image
	 *
	 * @param galleryImage Image to which add a contact
	 * @param contact Contact to tag
	 */
	private void addImageContact (GalleryImage galleryImage, Contact contact)
	{
		galleryImage.addPeople(contact);
		writeFile();
		setImageViewContactList(galleryImage);
	}


	/**
	 * Show image panel. Image of the panel has to be set before
	 */
	private void showImageDetail ()
	{
		layout.show(routerPanel, VIEW_IMAGE);
	}


	/**
	 * Build imageList from folder content if no data file has been set previously
	 */
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


	/**
	 * Show contact selection view
	 */
	void showContactSelectionPanel ()
	{
		contactSelectView.updateList();
		layout.show(routerPanel, VIEW_CONTACTS);
	}


	/**
	 * Copy a file from a source to a destination
	 *
	 * @param source File to be copied
	 * @param destination New file name
	 * @throws IOException If there is any problem copying the file
	 */
	private void copyFile (String source, String destination)
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

		/**
		 * Action performed when clicking on an image in the main view
		 *
		 * @param actionEvent event attached to the image button in the main view
		 */
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

		/**
		 * Action performed when clicking on an image in the main view
		 *
		 * @param arg0 Event attached  to the add button in the main view
		 */
		@Override
		public void actionPerformed (ActionEvent arg0)
		{
			JFileChooser fileChooser = new JFileChooser();

			fileChooser.setMultiSelectionEnabled(true);

			fileChooser.setFileFilter(filter);

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File[] selectedImages = fileChooser.getSelectedFiles();

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
						addImage(galleryImage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	/**
	 * Add an image in the gallery application
	 *
	 * @param img Image to add in the gallery application
	 */
	void addImage (GalleryImage img)
	{
		imageList.add(img);
		writeFile();
		mainView.updateView();
	}


	/**
	 * Remove an image in the gallery application
	 *
	 * @param image Image to remove in the gallery application
	 */
	void removeImage (GalleryImage image)
	{
		imageList.remove(image);
		writeFile();
		mainView.updateView();
		layout.show(routerPanel, VIEW_MAIN);
		File file = new File(image.getPath());
		file.delete();
	}


	/**
	 * Listener for cancel button in edit form
	 */
	private class CancelListener implements ActionListener
	{

		/**
		 * Action performed when clicking on cancel button in edit form
		 *
		 * @param actionEvent event attached to the cancel button in edit form
		 */
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			layout.show(routerPanel, VIEW_MAIN);
		}
	}


	/**
	 *
	 */
	private class LoadedAppsListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			try {
				contactApp = (ContactApp) os.getLoadedApp(ContactApp.class);

				SelectContactListener selectContactListener = new SelectContactListener();

				contactSelectView = contactApp.getSelectContactPanel();
				contactSelectView.addSelectionListener(selectContactListener);

				routerPanel.add(contactSelectView, VIEW_CONTACTS);
				routerPanel.validate();
				routerPanel.repaint();

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
			addImageContact(imageView.getImage(), contact);
			showImageDetail();
		}

		@Override
		public void onCancel ()
		{
			System.out.println("onCancel triggered");
			showImageDetail();
		}
	}

}