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
	private static final String LABEL_ADD = "addContact.png";
	private static final String LABEL_EDIT = "edit.png";
	private static final String LABEL_DELETE = "delete.png";

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
	 * @param contactApp     Reference to the application
	 * @param newListener    Listener for the new contact button
	 * @param editListener   Listener for the edit contact button
	 * @param deleteListener Listener for the delete contact button
	 */
	ListMainView (ContactApp contactApp,
		      ActionListener newListener,
		      ActionListener editListener,
		      ActionListener deleteListener)
	{
		super(contactApp);

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
