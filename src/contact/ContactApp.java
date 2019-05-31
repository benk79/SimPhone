package contact;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import main.Application;
import main.Config;
import main.Serializer;

/**
 * Application de gestion des contacts
 *
 * @author Benjamin Keller
 * @version 0.1.0
 * @// TODO: 28.05.2019 Finaliser le nettoyage de code
 * @see main.Application
 */
public class ContactApp extends Application {

	private static final String LIST_VIEW = "LIST";

	private static final String DETAIL_VIEW = "DETAIL";

	private Contact[] contactList;

	private CardLayout layout;

	private ListView listView;

	private EditView detailView;

	/**
	 *
	 */
	public ContactApp()
	{
		super("Contacts", "contact.png");

		//
		contactList = new Contact[200];
		try {
			System.out.println(getDataPath() + "contacts.ser");
			contactList = (Contact[]) Serializer.get(getDataPath() + "contacts.ser");
		} catch (FileNotFoundException e) {
			System.out.println("oops - Contact app could not open file contacts.ser");
			System.out.println("\tThis can happen if there are still no contact added.");
		} catch (Exception e) {
			System.out.println("oops - Contact app could not open file contacts.ser");
			e.printStackTrace();
		}
	}


	public void onInit()
	{
		layout = new CardLayout();

		screen.setLayout(layout);


		int w = os.getAppScreenWidth();
		int h = os.getAppScreenHeight();

		Dimension dim = new Dimension(w, h);
		screen.setPreferredSize(dim);


		/**
		 * List View
		 */

		listView = new ListView(contactList);

		AddBouttonListener newContact = new AddBouttonListener();
		EditBouttonListener editBouttonListener = new EditBouttonListener();
		DeleteListener deleteListener = new DeleteListener();

		listView.setMainMode(newContact, editBouttonListener, deleteListener);


		/**
		 * Detail View
		 */

		detailView = new EditView();

		SaveContactListener saveContactListener = new SaveContactListener();
		detailView.addSaveListener(saveContactListener);

		CancelListener cancelListener = new CancelListener();
		detailView.addCancelListener(cancelListener);

		screen.add(listView, LIST_VIEW);
		screen.add(detailView, DETAIL_VIEW);


		showView(LIST_VIEW);

	}

	public ListView getSelectContactPanel (ActionListener selectListener, ActionListener cancelListener)
	{
		ListView selectPanel = new ListView(contactList);

		selectPanel.setSelectMode(selectListener, cancelListener);

		// selectPanel.addCancelListener(cancelListener);
		// selectPanel.addSelectContactListener(selectListener);

		//selectPanel.updateList();

		return selectPanel;
	}

	static String getDataPath ()
	{
		return Config.DATA_PATH + "/contact/";
	}

	/**
	 *
	 */
	private void showUpdatedList ()
	{
		listView.updateList();
		showView(LIST_VIEW);
	}

	private void showView (String viewName)
	{
		layout.show(screen, viewName);
	}

	private void writeFile ()
	{
		try {
			Serializer.set(getDataPath() + "contacts.ser", contactList);
		} catch (Exception e) {
			System.out.println("oops");
			e.printStackTrace();
		}
	}


	private void insertContact (Contact c)
	{
		int id = c.insertId();
		contactList[id] = c;
		/**
		 * @// TODO: 28.05.2019 Allonger le tableau si nécessaire 
		 */
	}


	class AddBouttonListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			detailView.setContact(new Contact());
			showView(DETAIL_VIEW);
		}
	}

	class EditBouttonListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			ContactButton cb = (ContactButton) actionEvent.getSource();
			detailView.setContact(cb.getContact());
			showView(DETAIL_VIEW);
		}
	}

	class DeleteListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			System.out.println("Deleting contact !");
			ContactButton cb = (ContactButton) actionEvent.getSource();

			Contact c = cb.getContact();
			contactList[c.getId()] = null;

			writeFile();
			showUpdatedList();
		}
	}

	class SaveContactListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			Contact contact = detailView.getContact();

			int contactId = contact.getId();

			if (contactId == -1) {
				insertContact(contact);
			}

			writeFile();
			showUpdatedList();
		}
	}


	class CancelListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			showView(LIST_VIEW);
		}
	}


}
