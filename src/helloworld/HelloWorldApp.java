package helloworld;

import javax.swing.*;

import contact.ListSelectView;
import main.Application;

import contact.ContactApp;
import contact.Contact;
import contact.ContactButton;
import main.SelectionListener;
import main.SeletionPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloWorldApp extends Application {

	private ContactApp contactApp;

	public HelloWorldApp()
	{
		super("HelloWorld", "hello.png");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");

		screen.add(titleLabel);


		// JPanel selectPanel
	}

	public void onInit ()
	{
		LoadedAppsListener loadedAppsListener = new LoadedAppsListener();
		os.addLoadedAppsListener(loadedAppsListener);
	}

	ListSelectView selectionPanel;
	JLabel selectedContact;
	//ContactAppSelectListener selectListener;
	//ContactAppCancelListener cancelListener;

	SelectionListener contactSelectionListener;

	private void addContactSelection ()
	{
		JButton selectContactBtn = new JButton("Select a contact");
		selectedContact = new JLabel();
		screen.add(selectContactBtn);
		screen.add(selectedContact);

		contactSelectionListener = new SelectContactListener();


		//selectListener = new ContactAppSelectListener();
		//cancelListener = new ContactAppCancelListener();

		SelectBtnListener selectBtnListener = new SelectBtnListener();
		selectContactBtn.addActionListener(selectBtnListener);

		selectionPanel = contactApp.getSelectContactPanel();
		selectionPanel.addSelectionListener(contactSelectionListener);

	}

	private class SelectBtnListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			System.out.println("select contact click");
			selectionPanel.updateList();
			screen.add(selectionPanel);

			screen.validate();
			screen.repaint();
		}
	}

	private class LoadedAppsListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{

			try {
				contactApp = (ContactApp) os.getLoadedApp(ContactApp.class);
				addContactSelection();
			} catch (ClassNotFoundException e) {
				System.out.println("Could not find ");
			}

		}
	}

	private class SelectContactListener implements SelectionListener
	{

		@Override
		public void onSelect (Object o)
		{
			Contact c = (Contact) o;

			selectedContact.setText(c.toString());
			screen.remove(selectionPanel);
			screen.validate();
			screen.repaint();
		}

		@Override
		public void onCancel ()
		{
			System.out.println("onCancel triggered");
			screen.remove(selectionPanel);
			screen.validate();
			screen.repaint();
		}
	}

	/*
	private class ContactAppSelectListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			ContactButton cb = (ContactButton) event.getSource();
			Contact c = cb.getContact();

			selectedContact.setText(c.toString());
			screen.remove(selectionPanel);
			screen.validate();
			screen.repaint();
		}
	}

	private class ContactAppCancelListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{

			screen.remove(selectionPanel);
			screen.validate();
			screen.repaint();
		}
	}*/

}
