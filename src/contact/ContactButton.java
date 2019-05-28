package contact;

import javax.swing.JButton;

/**
 * The ContactButton extends JButton and has a Contact linked to it
 * allowing a simpler use of ActionEvent from controller code
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
public class ContactButton extends JButton
{
	/**
	 * Contact attached to the button
	 */
	private Contact contact;

	/**
	 * @param contact Contact to attach to the button
	 * @param text    Text to display in the button
	 */
	public ContactButton (Contact contact, String text)
	{
		super(text);
		this.contact = contact;
	}

	/**
	 * @return Contact attached to button
	 */
	public Contact getContact ()
	{
		return contact;
	}
}
