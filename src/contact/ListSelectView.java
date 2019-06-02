package contact;

import java.awt.event.ActionListener;


/**
 * List used for selecting a contact from another application
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
class ListSelectView extends ListView
{
	private static final String LABEL_SELECT = "Select";
	private static final String LABEL_CANCEL = "Cancel";


	/**
	 * Listener for the select contact button
	 */
	private ActionListener selectListener;


	/**
	 * Listener for the select contact button
	 */
	private ActionListener cancelListener;


	/**
	 * Constructor of the list select view
	 *
	 * @param contactApp     Reference to the application
	 * @param selectListener Listener for the select contact button
	 * @param cancelListener Listener for the cancel button
	 */
	ListSelectView (ContactApp contactApp,
			ActionListener selectListener,
			ActionListener cancelListener)
	{
		super(contactApp);

		this.selectListener = selectListener;
		this.cancelListener = cancelListener;

		initListView();
	}


	/**
	 * Add buttons linked to a specific Contact
	 *
	 * @param contact Contact to which add the buttons
	 */
	@Override
	protected void addContactButtons (Contact contact)
	{
		addContactButton(contact, LABEL_SELECT, selectListener);
	}


	/**
	 * Add buttons to the bottom menu of the list
	 */
	@Override
	protected void addMenuButtons ()
	{

		addMenuButton(LABEL_CANCEL, cancelListener);
	}

}
