package helloworld;

import javax.swing.*;

import contact.ListSelectView;
import main.Application;

import contact.ContactApp;
import contact.Contact;
import contact.ContactButton;
import main.SelectionListener;
import main.SeletionPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloWorldApp extends Application {

	private ContactApp contactApp;

	public HelloWorldApp()
	{
		super("HelloWorld", "hello.png");

		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");
		titleLabel.setForeground(Color.WHITE);
		screen.add(titleLabel);
		

		// JPanel selectPanel
	}

	public void onInit ()
	{
		LoadedAppsListener loadedAppsListener = new LoadedAppsListener();
		os.addLoadedAppsListener(loadedAppsListener);
		screen.validate();
		screen.repaint();
	}

	ListSelectView selectionPanel;
	JLabel selectedContact;
	JTextArea selectedContactText;
	//ContactAppSelectListener selectListener;
	//ContactAppCancelListener cancelListener;

	SelectionListener contactSelectionListener;

	private void addContactSelection ()
	{
		JButton selectContactBtn = new JButton("Select a contact");
		selectContactBtn.setBackground(Color.YELLOW);
		selectContactBtn.setForeground(Color.BLACK);
		
		
		selectedContactText = new JTextArea ();
		selectedContact = new JLabel ();
		
		screen.add(selectContactBtn);
		screen.add(selectedContact);
		screen.setBackground(Color.BLACK);
		
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
			
			screen.validate();
			screen.repaint();

		}
	}

	private class SelectContactListener implements SelectionListener
	{

		@Override
		public void onSelect (Object o)
		{
			Contact c = (Contact) o;
			selectedContact.setForeground(Color.WHITE);
			selectedContact.setText(c.toString() );
			selectedContact.setPreferredSize(new Dimension(350, 400)); 
			
			Font police = new Font("Arial", Font.PLAIN, 17);
			selectedContact.setFont(police);
			selectedContact.setText("<html>Hello, je m'appelle " + c.getFirstName() + " " + c.getLastName() + "<br/>"
			+ "\nMon email est : " + c.getEmail() + "<br/>" 
			+ "\nMon numéro de téléphone est : " + c.getPhoneNumber() + "<br/>" 
			+ "Je suis né le " + c.getBirthDate() + "<br/>" 
			+ " Mon image de profil se trouve : " + c.getImage() + "</html>");

			
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
