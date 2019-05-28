package contact;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditView extends JPanel
{
	private Contact contact;

	private JButton saveButton;

	private JButton cancelButton;

	private JTextField firstNameField;

	private JTextField lastNameField;

	private JTextField emailField;

	private JTextField phoneNumberField;

	private ActionListener savedContactListener;

	public EditView ()
	{
		this.contact = new Contact();

		initView();
	}

	public EditView (Contact contact)
	{
		this.contact = contact;

		initView();
	}


	public void setContact (Contact contact)
	{
		this.contact = contact;

		setViewValues();
	}


	public Contact getContact ()
	{
		return contact;
	}


	public void addSaveListener (ActionListener saveListener)
	{
		//saveButton.addActionListener(saveListener);
		savedContactListener = saveListener;
	}

	/**
	 * @param cancelListener
	 */
	public void addCancelListener (ActionListener cancelListener)
	{
		cancelButton.addActionListener(cancelListener);

	}


	/**
	 *
	 */
	private void initView ()
	{
		firstNameField = new JTextField(20);
		lastNameField = new JTextField(20);
		emailField = new JTextField(20);
		phoneNumberField = new JTextField(20);


		saveButton = new JButton("Save");
		SaveContactListener saveContactListener = new SaveContactListener();
		saveButton.addActionListener(saveContactListener);

		cancelButton = new JButton("Cancel");

		add(new JLabel("First name: "));
		add(firstNameField);

		add(new JLabel("Last name: "));
		add(lastNameField);

		add(saveButton);
		add(cancelButton);
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
