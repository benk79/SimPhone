package contact;

import java.awt.event.ActionListener;


/**
 * List used for managing contacts in the application
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
public class ListMainView extends ListView
{
	private static final String LABEL_ADD = "New contact";
	private static final String LABEL_EDIT = "Edit";
	private static final String LABEL_DELETE = "Delete";

	/**
	 * Listener for the edit contact button (main mode)
	 */
	private ActionListener editListener;


	/**
	 * Listener for the delete contact button (main mode)
	 */
	private ActionListener deleteListener;


	/**
	 * Listener for the delete contact button (main mode)
	 */
	private ActionListener newListener;


	/**
	 * Constructor of the list main view
	 *
	 * @param contactList    List of contacts to display in the list view
	 * @param newListener    Listener for the new contact button
	 * @param editListener   Listener for the edit contact button
	 * @param deleteListener Listener for the delete contact button
	 */
	ListMainView (Contact[] contactList,
		      ActionListener newListener,
		      ActionListener editListener,
		      ActionListener deleteListener)
	{
		super(contactList);

		this.newListener = newListener;
		this.editListener = editListener;
		this.deleteListener = deleteListener;

		initListView();
	}


	/**
	 * Add buttons linked to a specific Contact
	 *
	 * @param contact Contact to which add the buttons
	 */
	@Override
	protected void addContactButtons (Contact contact)
	{
		addContactButton(contact, LABEL_EDIT, editListener);
		addContactButton(contact, LABEL_DELETE, deleteListener);
	}


	/**
	 * Add buttons to the bottom menu of the list
	 */
	@Override
	protected void addMenuButtons ()
	{
		addMenuButton(LABEL_ADD, newListener);
	}

}
