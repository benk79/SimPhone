package contact;

import gallery.GalleryImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Edit contact view
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
class EditView extends JPanel
{
	/**
	 * Contact linked to the edit form
	 */
	private Contact contact;


	/**
	 * Text field for first name
	 */
	private JTextField firstNameField;


	/**
	 * Text field for last name
	 */
	private JTextField lastNameField;


	/**
	 * Text field for email address
	 */
	private JTextField emailField;


	/**
	 * Text field for phone number
	 */
	private JTextField phoneNumberField;


	private ContactApp contactApp;

	private JTextField dateTextField;

	private JButton imageChooseBtn;


	private GalleryImage image;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


	/**
	 * GridBagConstraints used to build the form
	 */
	private GridBagConstraints gbc;


	/**
	 * Listener to delegate the save action after updating
	 * a contact
	 */
	private ActionListener savedContactListener;


	/**
	 * Constructor of the edit view
	 *
	 * @param savedListener Listener for the save button
	 * @param cancelListener Listener for the cancel button
	 */
	EditView (ContactApp contactApp,
		  ActionListener savedListener,
		  ActionListener cancelListener)
	{
		this.contactApp = contactApp;
		savedContactListener = savedListener;


		/*
		 * Build form elements
		 */

		firstNameField = new JTextField(20);
		lastNameField = new JTextField(20);
		emailField = new JTextField(20);
		phoneNumberField = new JTextField(20);

		dateTextField = new JTextField(20);
		dateTextField.setColumns(20);

		//
		JButton saveButton = new JButton("Save");
		SaveContactListener saveContactListener = new SaveContactListener();
		saveButton.addActionListener(saveContactListener);

		//
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(cancelListener);


		/*
		 * Build the form
		 */

		setLayout(new GridBagLayout());

		//
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridy = 0;

		//
		JPanel imagePanel = new JPanel();
		gbc.gridwidth = 2;

		imagePanel.setBackground(Color.yellow);
		add(imagePanel, gbc);

		imageChooseBtn = new JButton("Choose an image");

		imageChooseBtn.setPreferredSize(new Dimension(400, 400));
		imagePanel.add(imageChooseBtn);

		//
		gbc.gridy++;
		gbc.gridwidth = 1;

		//
		addTextField(firstNameField, "First name: ");
		addTextField(lastNameField, "Last name: ");
		addTextField(phoneNumberField, "Phone number: ");
		addTextField(emailField, "Email address: ");
		addTextField(dateTextField, "Birth date: ");

		//
		gbc.gridx = 0;
		add(cancelButton, gbc);

		//
		gbc.gridx = 1;
		add(saveButton, gbc);
	}


	/**
	 * Link a contact to the edit view
	 *
	 * @param contact Contact to link
	 */
	void setContact (Contact contact)
	{
		this.contact = contact;

		setViewValues();
	}

	/**
	 * Link an image to save with the contact
	 *
	 * @param img GalleryImage for the contact
	 */
	void setImage (GalleryImage img)
	{
		image = img;
		System.out.println(image.getPath());
		//setViewValues();
	}


	/**
	 * Get the contact linked to the edit view
	 *
	 * @return Contact linked
	 */
	Contact getContact ()
	{
		return contact;
	}


	/**
	 * Add a text field with it's label to the form
	 *
	 * @param field Text field to add
	 * @param label Label for the text field
	 */
	private void addTextField (JTextField field, String label)
	{
		gbc.gridx = 0;
		add(new JLabel(label), gbc);

		gbc.gridx = 1;
		add(field, gbc);

		gbc.gridy++;
	}


	/**
	 * Set the fields values with the current Contact
	 */
	private void setViewValues ()
	{
		firstNameField.setText(contact.getFirstName());
		lastNameField.setText(contact.getLastName());
		emailField.setText(contact.getEmail());
		phoneNumberField.setText(contact.getPhoneNumber());
		// dateTextField.setValue(new Date());
	}


	private boolean validDate (String test)
	{
		//String test = "02/01/20";
		//String format = "dd/MM/yyyy";
		//SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		dateFormat.setLenient(false);
		try {
			Date date = dateFormat.parse(test);
			if (!dateFormat.format(date).equals(test)) {
				return false;
				//throw new ParseException(test + " is not a valid format for " + format, 0);
			}
			return true;
		} catch (ParseException ex) {
			return false;
			//ex.printStackTrace();
		}

	}

	/**
	 * When clicking on save button, set contact properties with
	 * actual values in the form.
	 */
	class SaveContactListener implements ActionListener
	{
		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			ArrayList<String> errors = new ArrayList<String>();

			String dateString = dateTextField.getText();

			if (!dateString.equals("")) {
				if (!validDate(dateString)) {
					errors.add("Birth date is not in a valid format (dd.mm.yyyy)");
					System.out.println("Birth date is not in a valid format (dd.mm.yyyy)");
					return;
				}
				// contact.setDate()
			}

			contact.setEmail(emailField.getText());
			contact.setFirstName(firstNameField.getText());
			contact.setPhoneNumber(phoneNumberField.getText());
			contact.setLastName(lastNameField.getText());


			System.out.println(dateTextField.getText());

			savedContactListener.actionPerformed(actionEvent);
		}
	}

	class ChooseImageListener implements ActionListener
	{

		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			contactApp.showImageSelectionPanel();
		}
	}
}
