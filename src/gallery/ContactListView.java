package gallery;

import contact.Contact;
import contact.ContactApp;
import contact.ContactButton;
import contact.ListSelectView;
import main.SelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ContactListView extends ListSelectView
{
	protected String LABEL_SELECT = "delete.png";

	private ArrayList<Contact> _contactList = new ArrayList<Contact>();

	public ContactListView (ContactApp contactApp)
	{
		super(contactApp);
		//_contactList = new ArrayList<Contact>();
	}

	@Override
	protected void addMenuButtons ()
	{
	}


	protected String getSelectLabel ()
	{
		return LABEL_SELECT;
	}

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

	void setList (ArrayList<Contact> contacts)
	{
		_contactList = contacts;
		updateList();
	}

	/*public void addContact(Contact c)
	{
		con
	}*/
}
