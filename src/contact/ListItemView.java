package contact;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * ListItemView extends a JPanel to show Contact items in a list
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
class ListItemView extends JPanel
{
	private JButton editButton;
	private JButton deleteButton;

	/**
	 * @param contact Contact to display in list
	 */
	ListItemView (Contact contact)
	{
		String name = contact.getFirstName() + " " + contact.getLastName();

		//
		editButton = new ContactButton(contact, "Edit");
		deleteButton = new ContactButton(contact, "Delete");

		//
		add(new JLabel(name));
		add(editButton);
		add(deleteButton);
	}

	/**
	 * @param al ActionListener to add to the edit button
	 */
	void addEditListener (ActionListener al)
	{
		editButton.addActionListener(al);
	}

	/**
	 * @param al ActionListener to add to the delete button
	 */
	void addDeleteListener (ActionListener al)
	{
		deleteButton.addActionListener(al);
	}
}
