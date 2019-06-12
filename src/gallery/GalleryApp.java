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
	 *
	 */
	private static final String VIEW_MAIN = "MAIN_VIEW";

	/**
	 *
	 */
	private static final String VIEW_IMAGE = "DETAIL_VIEW";

	/**
	 *
	 */
	private static final String VIEW_CONTACTS = "CONTACT_VIEW";

	/**
	 *
	 */
	private JPanel routerPanel;

	/**
	 *
	 */
	private ImageView imageView;

	/**
	 *
	 */
	private ListView mainView;

	/**
	 *
	 */
	private ArrayList<GalleryImage> imageList;

	/**
	 *
	 */
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");

	/**
	 *
	 */
	private ContactApp contactApp;

	/**
	 *
	 */
	private contact.ListSelectView contactSelectView;

	/**
	 *
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
		ListSelectView selectView = new ListSelectView(imageList);

		return selectView;
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


	private void addImageContact (GalleryImage galleryImage, Contact contact)
	{
		galleryImage.addPeople(contact);
		writeFile();
		setImageViewContactList(galleryImage);
	}

	private void showImageDetail ()
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

	void addImage (GalleryImage img)
	{
		imageList.add(img);
		writeFile();
		mainView.updateView();
	}

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