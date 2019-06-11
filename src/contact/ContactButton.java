package contact;

import javax.swing.JButton;

import main.ButtonIcon;


/**
 * The ContactButton extends JButton and has a Contact linked to it
 * allowing a simpler use of ActionEvent from controller code
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
public class ContactButton extends ButtonIcon
{
	/**
	 * Contact attached to the button
	 */
	private Contact contact;


	/**
	 * Constructor of the ContactButton
	 *
	 * @param contact Contact to attach to the button
	 * @param text    Text to display in the button
	 */
	public ContactButton (Contact contact, String way)
	{
		super(way);
		this.contact = contact;
	}


	/**
	 * Get the Contact linked to the ContactButton
	 *
	 * @return Contact attached to button
	 */
	public Contact getContact ()
	{
		return contact;
	}
}
