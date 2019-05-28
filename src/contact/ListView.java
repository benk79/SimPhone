package contact;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ListView extends JPanel {

	private JButton addButton;
	private Contact[] contactList;
	private JPanel list;

	private ActionListener editContactListener;
	private ActionListener deleteContactListener;


	public ListView (Contact[] contactList)
	{
		this.contactList = contactList;

		JPanel menu = new JPanel();

		//JPanel listContainer = new JPanel();
		list = new JPanel(new GridBagLayout());

		addButton = new JButton("New contact");

		
		setLayout(new BorderLayout());

		add(new JScrollPane(list));
		add(menu, BorderLayout.SOUTH);

		menu.add(addButton);
	}

	public void updateList ()
	{
		list.removeAll();

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.gridx = 0;

		for (Contact contact : contactList) {
			if (contact instanceof Contact) {
				ListItemView item = new ListItemView(contact);
				item.addEditListener(editContactListener);
				item.addDeleteListener(deleteContactListener);
				list.add(item, c);
				c.gridy++;
			}
		}

		list.revalidate();
		list.repaint();
	}

	public void addEditContactListener (ActionListener editContactListener)
	{
		this.editContactListener = editContactListener;
	}


	public void addDeleteContactListener (ActionListener deleteContactListener)
	{
		this.deleteContactListener = deleteContactListener;
	}

	public void addNewContactListener (ActionListener newContactListener)
	{
		addButton.addActionListener(newContactListener);
	}


}
