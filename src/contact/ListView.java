package contact;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class ListView extends JPanel
{

	/**
	 * Text definitions
	 */

	private static final String LABEL_ADD = "New contact";
	private static final String LABEL_EDIT = "Edit";
	private static final String LABEL_DELETE = "Delete";
	private static final String LABEL_SELECT = "Select";
	private static final String LABEL_CANCEL = "Cancel";


	/**
	 * List of contacts to display
	 */
	private Contact[] contactList;


	/**
	 * The mode of the list view can be:
	 * 0 - normal application mode
	 * 1 - select contact mode
	 */
	private int mode = 0;


	/**
	 * Container for the contact items
	 */
	private JPanel list;


	/**
	 * New contact button (main mode)
	 */
	private JButton addButton;


	/**
	 * Cancel button (select mode)
	 */
	private JButton cancelButton;


	/**
	 * Listener for the edit contact button (main mode)
	 */
	private ActionListener editContactListener;


	/**
	 * Listener for the delete contact button (main mode)
	 */
	private ActionListener deleteContactListener;


	/**
	 * Listener for the select contact button (select mode)
	 */
	private ActionListener selectContactListener;


	/**
	 * Constructor of the list view
	 *
	 * @param contactList List of contacts to display in the list view
	 */
	ListView (Contact[] contactList)
	{
		this.contactList = contactList;
	}


	/**
	 * Set the select mode for the list.
	 * <p>
	 * This mode is used when another application needs to select a contact
	 *
	 * @param selectListener Listener for the select contact button
	 * @param cancelListener Listener for the cancel button
	 */
	void setSelectMode (ActionListener selectListener, ActionListener cancelListener)
	{
		mode = 1;

		selectContactListener = selectListener;

		initListView();

		cancelButton.addActionListener(cancelListener);
	}


	/**
	 * Set the main mode for the list.
	 * <p>
	 * In main mode it is possible to add, edit and delete contacts.
	 * This is the default mode used by the Contact Application
	 *
	 * @param newListener    Listener for the new contact button
	 * @param editListener   Listener for the edit contact button
	 * @param deleteListener Listener for the delete contact button
	 */
	void setMainMode (
		ActionListener newListener,
		ActionListener editListener,
		ActionListener deleteListener)
	{
		mode = 0;

		editContactListener = editListener;
		deleteContactListener = deleteListener;

		initListView();

		addButton.addActionListener(newListener);
	}


	/**
	 * Update content of list with current array of contacts
	 */
	void updateList ()
	{
		list.removeAll();

		//
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridy = 0;
		c.gridx = 0;

		//
		for (Contact contact : contactList) {
			if (contact != null) {
				addContact(contact, c);
			}
		}

		//
		c.weighty = 1;
		list.add(new JLabel(""), c);

		//
		list.revalidate();
		list.repaint();
	}


	/**
	 * Initialize the list view.
	 * The mode of the list should be defined before calling this function
	 */
	private void initListView ()
	{
		setLayout(new BorderLayout());

		//
		list = new JPanel(new GridBagLayout());
		add(new JScrollPane(list));

		//
		JPanel menu = new JPanel();

		switch (mode) {
			case 0:
				addButton = new JButton(LABEL_ADD);
				menu.add(addButton);
				break;
			case 1:
				cancelButton = new JButton(LABEL_CANCEL);
				menu.add(cancelButton);
				break;
		}

		add(menu, BorderLayout.SOUTH);

		//
		updateList();
	}


	/**
	 * Add a contact item to the list of contacts
	 *
	 * @param contact Contact to display
	 * @param c       GridBagConstraint used for the list of contacts
	 */
	private void addContact (Contact contact, GridBagConstraints c)
	{
		String name = contact.getFirstName() + " " + contact.getLastName();

		//
		c.gridx = 0;
		c.weightx = 1;
		list.add(new JLabel(name), c);

		//
		c.weightx = 0;
		c.gridx = 1;

		switch (mode) {
			case 0:
				JButton editButton = new ContactButton(contact, LABEL_EDIT);
				JButton deleteButton = new ContactButton(contact, LABEL_DELETE);

				list.add(editButton, c);

				c.gridx = 2;
				list.add(deleteButton, c);

				editButton.addActionListener(editContactListener);
				deleteButton.addActionListener(deleteContactListener);
				break;
			case 1:
				JButton selectButton = new ContactButton(contact, LABEL_SELECT);

				list.add(selectButton, c);

				selectButton.addActionListener(selectContactListener);
				break;

		}

		//
		c.gridy++;
	}
}
