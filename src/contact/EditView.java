package contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class EditView extends JPanel
{
	private Contact contact;

	private JButton saveButton;

	private JButton cancelButton;

	private JTextField firstNameField;

	private JTextField lastNameField;

	private JTextField emailField;

	private JTextField phoneNumberField;

	private ActionListener savedContactListener;

	EditView ()
	{
		this.contact = new Contact();

		initView();
	}


	void setContact (Contact contact)
	{
		this.contact = contact;

		setViewValues();
	}


	Contact getContact ()
	{
		return contact;
	}


	void addSaveListener (ActionListener saveListener)
	{
		//saveButton.addActionListener(saveListener);
		savedContactListener = saveListener;
	}

	/**
	 * @param cancelListener
	 */
	void addCancelListener (ActionListener cancelListener)
	{
		cancelButton.addActionListener(cancelListener);

	}


	/**
	 *
	 */
	private void initView ()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 5, 5);

		firstNameField = new JTextField(20);
		lastNameField = new JTextField(20);
		emailField = new JTextField(20);
		phoneNumberField = new JTextField(20);


		saveButton = new JButton("Save");
		SaveContactListener saveContactListener = new SaveContactListener();
		saveButton.addActionListener(saveContactListener);

		cancelButton = new JButton("Cancel");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("First name: "), gbc);

		gbc.gridx = 1;
		add(firstNameField, gbc);


		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Last name: "), gbc);

		gbc.gridx = 1;
		add(lastNameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Phone number: "), gbc);

		gbc.gridx = 1;
		add(phoneNumberField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Email address: "), gbc);

		gbc.gridx = 1;
		add(emailField, gbc);

		gbc.gridy = 4;
		gbc.gridx = 1;
		add(saveButton, gbc);
		gbc.gridx = 0;
		add(cancelButton, gbc);
	}

	private void setViewValues ()
	{
		firstNameField.setText(contact.getFirstName());
		lastNameField.setText(contact.getLastName());
		emailField.setText(contact.getEmail());
		phoneNumberField.setText(contact.getPhoneNumber());
	}

	class SaveContactListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			contact.setEmail(emailField.getText());
			contact.setFirstName(firstNameField.getText());
			contact.setPhoneNumber(phoneNumberField.getText());
			contact.setLastName(lastNameField.getText());

			savedContactListener.actionPerformed(actionEvent);
		}
	}
}
