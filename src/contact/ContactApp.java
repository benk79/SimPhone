package contact;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import main.Application;
import main.Config;
import main.SeletionPanel;
import main.Serializer;

import javax.swing.*;

/**
 * Application de gestion des contacts
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
public class ContactApp extends Application
{
	/**
	 * Name of list view for card layout
	 */
	private static final String LIST_VIEW = "LIST";


	/**
	 * Name of detail view for card layout
	 */
	private static final String DETAIL_VIEW = "DETAIL";


	/**
	 *  List of contacts
	 */
	private Contact[] contactList;


	/**
	 * Quantity to increment list of contacts
	 */
	private int incrementListQty = 10;


	/**
	 *  Card layout to switch between views
	 */
	private CardLayout layout;


	/**
	 *  List view to manage contacts
	 */
	private ListView listView;


	/**
	 *  Detail view of contact to edit
	 */
	private EditView detailView;


	/**
	 * Constructor of the ContactApp class
	 */
	public ContactApp ()
	{
		super("Contacts", "contact.png");

		//
		contactList = new Contact[incrementListQty];
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


	/**
	 * Initialization of the class when os property has been set
	 */
	public void onInit ()
	{
		layout = new CardLayout();

		screen.setLayout(layout);

		int w = os.getAppScreenWidth();
		int h = os.getAppScreenHeight();

		Dimension dim = new Dimension(w, h);
		screen.setPreferredSize(dim);


		/*
		 * List View
		 */

		AddBouttonListener newContact = new AddBouttonListener();
		EditBouttonListener editBouttonListener = new EditBouttonListener();
		DeleteListener deleteListener = new DeleteListener();

		listView = new ListMainView(this, newContact, editBouttonListener, deleteListener);


		/*
		 * Detail View
		 */

		SaveContactListener saveContactListener = new SaveContactListener();
		CancelListener cancelListener = new CancelListener();

		detailView = new EditView(saveContactListener, cancelListener);


		/*
		 * Finalisation of display
		 */

		screen.add(listView, LIST_VIEW);
		screen.add(detailView, DETAIL_VIEW);

		showView(LIST_VIEW);
	}


	/**
	 * Provide ListSelectView as JPanel to other applications to select a contact
	 *
	 * @return SeletionPanel with contact selection options
	 */
	public ListSelectView getSelectContactPanel ()
	{
		return new ListSelectView(this);
	}

	/**
	 * Provide ListSelectView as JPanel to other applications to select a contact
	 *
	 * @return SeletionPanel with contact selection options
	 * @deprecated
	 */
	public SeletionPanel getSelectContactPanel (ActionListener onSelect, ActionListener onCancel)
	{

		return new ListSelectView(this);
	}

	/**
	 * Get the path for read/write files and data
	 *
	 * @return The path for read/write data dedicated to this application
	 */
	static String getDataPath ()
	{
		return Config.DATA_PATH + "/contact/";
	}


	/**
	 * Get the contact list from the application
	 *
	 * @return contact list
	 */
	Contact[] getContactList ()
	{
		return contactList;
	}


	/**
	 * Update the list view and show it
	 */
	private void showUpdatedList ()
	{
		listView.updateList();
		showView(LIST_VIEW);
	}


	/**
	 * Show a predefined view in the card layout
	 * @param viewName Name of the view
	 */
	private void showView (String viewName)
	{
		layout.show(screen, viewName);
	}


	/**
	 * Write the list of contacts in it's file
	 */
	private void writeFile ()
	{
		try {
			Serializer.set(getDataPath() + "contacts.ser", contactList);
		} catch (Exception e) {
			System.out.println("oops");
			e.printStackTrace();
		}
	}


	/**
	 * Create contact id and insert it into the list of contacts
	 *
	 * @param c Contact to add in the list
	 */
	private void insertContact (Contact c)
	{
		int id = c.insertId();

		if (contactList.length <= id) {
			Contact[] newList = new Contact[contactList.length + incrementListQty];
			System.arraycopy(contactList, 0, newList, 0, contactList.length);
			contactList = newList;
		}

		contactList[id] = c;
	}


	/**
	 * Listener for new contact in list view menu
	 */
	class AddBouttonListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			detailView.setContact(new Contact());
			showView(DETAIL_VIEW);
		}
	}


	/**
	 * Listener for edit contact in list
	 */
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


	/**
	 * Listener for deleted contact in list
	 */
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


	/**
	 * Listener for saved contact in edit form
	 */
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


	/**
	 * Listener for cancel button in edit form
	 */
	class CancelListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			showView(LIST_VIEW);
		}
	}

}
