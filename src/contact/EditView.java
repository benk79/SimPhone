package contact;

import gallery.GalleryImage;
import gallery.ImageButton;
import main.ButtonIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

	private ImageButton imageChooseBtn;

	private String defaultPicture = "ressourcesSystem/man-user2.png";

	private GalleryImage defaultImage;

	private GalleryImage image;

	JPanel imagePanel;

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
	 * @param savedListener  Listener for the save button
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
		ButtonIcon saveButton = new ButtonIcon("save2.png");
		SaveContactListener saveContactListener = new SaveContactListener();
		saveButton.addActionListener(saveContactListener);

		//
		ButtonIcon cancelButton = new ButtonIcon("return.png");
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
		imagePanel = new JPanel();
		gbc.gridwidth = 2;

		imagePanel.setBackground(Color.BLACK);

		add(imagePanel, gbc);

//		imageChooseBtn = new JButton("Choose an image");
		try {
			defaultImage = new GalleryImage(defaultPicture);
		} catch (Exception e) {
			e.printStackTrace();
		}

		imageChooseBtn = new ImageButton(defaultImage, 400);
		ChooseImageListener chooseImageListener = new ChooseImageListener();
		//imageChooseBtn.setPreferredSize(new Dimension(400, 400));
		imageChooseBtn.addActionListener(chooseImageListener);
		imagePanel.add(imageChooseBtn);

		//
		gbc.gridy++;
		gbc.gridwidth = 1;

		//
		addTextField(firstNameField, "Pr�nom : ");
		addTextField(lastNameField, "Nom : ");
		addTextField(phoneNumberField, "Num�ro de t�l. : ");
		addTextField(emailField, "Email : ");
		addTextField(dateTextField, "Date de naissance (jj.mm.aaaa) : ");

		//
		gbc.weighty = 1;

		//
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		add(cancelButton, gbc);

		//
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LAST_LINE_END;
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

		//GalleryImage galleryImage = new GalleryImage()
		//imageChooseBtn = new ImageButton(img, 400);
		imageChooseBtn.setImage(image);

		//imagePanel.removeAll();
		//imagePanel.add(imageChooseBtn);

		revalidate();
		repaint();
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
		gbc.anchor = GridBagConstraints.LINE_START;

		gbc.gridx = 0;
		JLabel labeltext = new JLabel(label);
		labeltext.setForeground(Color.WHITE);
		labeltext.setBackground(Color.BLACK);
		add(labeltext, gbc);

		gbc.anchor = GridBagConstraints.LINE_END;

		gbc.gridx = 1;
		add(field, gbc);

		gbc.gridy++;
	}


	/**
	 * Set the fields values with the current Contact
	 */
	private void setViewValues ()
	{
		String img = contact.getImage();

		if (img == null)
			img = defaultPicture;

		try {
			image = new GalleryImage(img);
		} catch (Exception e) {
			image = defaultImage;
			contact.setImage(null);
			//e.printStackTrace();
		}

		setImage(image);

		//if (contact)
		firstNameField.setText(contact.getFirstName());
		lastNameField.setText(contact.getLastName());
		emailField.setText(contact.getEmail());
		phoneNumberField.setText(contact.getPhoneNumber());

		String birthDate = contact.getStringBirthDate();
		dateTextField.setText(birthDate);
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


			// First name required
			try {
				contact.setFirstName(firstNameField.getText());
				firstNameField.setBackground(Color.WHITE);
			} catch (Exception e) {
				firstNameField.setBackground(Color.red);
				errors.add(e.getMessage());
				System.out.println(e.getMessage());
				return;
			}


			// Validation Phone Number
			try {
				contact.setPhoneNumber(phoneNumberField.getText());
				phoneNumberField.setBackground(Color.WHITE);

			} catch (Exception e) {
				phoneNumberField.setBackground(Color.red);
				errors.add(e.getMessage());
				System.out.println(e.getMessage());
				return;
				// e.printStackTrace();
			}


			// Validation Email
			try {
				contact.setEmail(emailField.getText());
				emailField.setBackground(Color.WHITE);

			} catch (Exception e) {
				emailField.setBackground(Color.red);
				errors.add(e.getMessage());
				System.out.println(e.getMessage());
				return;
				// e.printStackTrace();
			}


			//Date validation
			try {
				contact.setBirthDate(dateTextField.getText());
				dateTextField.setBackground(Color.WHITE);
			} catch (Exception e) {
				dateTextField.setBackground(Color.red);
				errors.add(e.getMessage());
				System.out.println(e.getMessage());
				return;
				// e.printStackTrace();
			}


			String imagePath = image.getPath();
			if (!imagePath.equals(defaultPicture))
				contact.setImage(imagePath);

			contact.setLastName(lastNameField.getText());


			//System.out.println(dateTextField.getText());

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
