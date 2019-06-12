package gallery;

import contact.Contact;
import contact.ContactApp;
import contact.ListSelectView;

import java.util.ArrayList;


/**
 * Extends ListSelectView to show contacts linked to an image
 *
 * @author Benjamin Keller
 * @author Louise Bretz
 * @version 1.0.0
 */
public class ContactListView extends ListSelectView
{
	/**
	 * Boutton to show near the contact to delete a contact from
	 * the list of tagged ones
	 */
	private String LABEL_SELECT = "delete.png";

	/**
	 * List of contacts to show in this view
	 */
	private ArrayList<Contact> _contactList = new ArrayList<Contact>();


	/**
	 * Constructor of the ContactListView class
	 *
	 * @param contactApp Contact application
	 */
	public ContactListView (ContactApp contactApp)
	{
		super(contactApp);
	}


	/**
	 * Override the method of SelectView to show a menu button
	 */
	@Override
	protected void addMenuButtons ()
	{
	}


	/**
	 * Override the SelectView method to get the button selecting a contact
	 *
	 * @return the image path to use for the button
	 */
	@Override
	protected String getSelectLabel ()
	{
		return LABEL_SELECT;
	}


	/**
	 * Override ListView function to get list of contacts to show
	 *
	 * @return List of contacts to display in the list
	 */
	@Override
	protected Contact[] getList ()
	{
		if (_contactList == null)
			return new Contact[0];

		Contact[] contacts = new Contact[_contactList.size()];

		for (int i = 0; i < contacts.length; i++) {
			contacts[i] = _contactList.get(i);
		}

		return contacts;
	}


	/**
	 * Set the list of contacts for this view
	 *
	 * @param contacts Contact list to show in this view
	 */
	void setList (ArrayList<Contact> contacts)
	{
		_contactList = contacts;
		updateList();
	}
}
