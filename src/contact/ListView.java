package contact;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import main.ButtonIcon;


/**
 * Abstract class for building JPanel views with the list of contacts
 *
 * @author Louise Bretz, Benjamin Keller
 * @version 1.0.
 */
abstract class ListView extends JPanel
{
	/**
	 * Reference to application
	 */
	private ContactApp contactApp;


	/**
	 * Container for the contact items
	 */
	private JPanel list;


	/**
	 * Container for the menu under the list
	 */
	private JPanel menu;


	/**
	 * GridBagConstraints used for building the list
	 */
	private GridBagConstraints listGbc;


	/**
	 * Constructor of the list view
	 *
	 * @param contactApp Reference to the application
	 */
	ListView (ContactApp contactApp)
	{
		this.contactApp = contactApp;
	}


	/**
	 * Update content of list with current array of contacts
	 */
	void updateList ()
	{
		list.removeAll();
		
	
		//
		listGbc = new GridBagConstraints();
		listGbc.fill = GridBagConstraints.HORIZONTAL;
		listGbc.insets = new Insets(5, 5, 5, 5);
		listGbc.gridy = 0;
		listGbc.gridx = 0;

		//
		for (Contact contact : contactApp.getContactList()) {
			if (contact != null) {
				addContact(contact);
				listGbc.gridy++;
			}
		}

		//
		listGbc.weighty = 1;
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);

		list.add(new JLabel(""), listGbc);

		//
		list.revalidate();
		list.repaint();
	}


	/**
	 * Extending class should decide which buttons are added to menu
	 * <p>
	 * Method `addMenuButton` can be used for this purpose from the
	 * overridden method.
	 */
	protected abstract void addMenuButtons ();


	/**
	 * Extending class should decide which buttons are attached to a Contact
	 * <p>
	 * Method `addContactButton` can be used for this purpose from the
	 * overridden method.
	 *
	 * @param contact Contact to which attach buttons
	 */
	protected abstract void addContactButtons (Contact contact);


	/**
	 * Add a button for a Contact on it's right in the list
	 *
	 * @param contact        Concerned contact
	 * @param label          Label for the button
	 * @param actionListener Listener for the button
	 */
	void addContactButton (Contact contact, String label, ActionListener actionListener)
	{
		ContactButton contactButton = new ContactButton(contact, label);
		contactButton.addActionListener(actionListener);

		list.add(contactButton, listGbc);
		listGbc.gridx++;
	}
	

	/**
	 * Add a button to the bottom menu of the list
	 *
	 * @param label          Label for the button
	 * @param actionListener ActionListener to add to the button
	 */
	void addMenuButton (String label, ActionListener actionListener)
	{
		ButtonIcon button = new ButtonIcon(label);
		button.addActionListener(actionListener);

		menu.add(button);
	}


	/**
	 * Initialize the list view.
	 */
	void initListView ()
	{
		setLayout(new BorderLayout());

		//
		list = new JPanel(new GridBagLayout());
		list.setBackground(Color.BLACK);
		list.setForeground(Color.WHITE);
		add(new JScrollPane(list));

		//
		menu = new JPanel();
		menu.setBackground(Color.BLACK);
		menu.setForeground(Color.WHITE);
		addMenuButtons();
		add(menu, BorderLayout.SOUTH);

		//
		updateList();
	}


	/**
	 * Add a contact item to the list of contacts
	 *
	 * @param contact Contact to display
	 */
	private void addContact (Contact contact)
	{
		String name = contact.getFirstName() + " " + contact.getLastName();

		//
		listGbc.gridx = 0;
		listGbc.weightx = 1;
		JLabel tagName = new JLabel(name);
		tagName.setFont(new Font("Arial", Font.PLAIN, 17));
		tagName.setForeground(Color.WHITE);
		list.add(tagName, listGbc);

		//
		listGbc.weightx = 0;
		listGbc.gridx = 1;

		addContactButtons(contact);
	}

}
