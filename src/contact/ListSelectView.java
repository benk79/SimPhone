package contact;

import main.SelectionListener;
import main.SeletionPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * List used for selecting a contact from another application
 *
 * @author Benjamin Keller
 * @version 1.0.0
 */
public class ListSelectView extends ListView implements SeletionPanel
{
	private static final String LABEL_SELECT = "tag.png";
	private static final String LABEL_CANCEL = "return.png";

	private SelectionListener selectionListener;

	/**
	 * Listener for the select contact button
	 */
	private ActionListener selectListener;


	/**
	 * Listener for the select contact button
	 */
	//private ActionListener cancelListener;


	/**
	 * Constructor of the list select view
	 *
	 * @param contactApp     Reference to the application
	 */
	ListSelectView (ContactApp contactApp)
	{
		super(contactApp);

		selectListener = new SelectListener();

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
		/*ActionListener selectListener = actionEvent -> {
			ContactButton cb = (ContactButton) actionEvent.getSource();
			Contact c = cb.getContact();

			selectionListener.onSelect(c);
		}; */
		addContactButton(contact, LABEL_SELECT, selectListener);
	}


	/**
	 * Add buttons to the bottom menu of the list
	 */
	@Override
	protected void addMenuButtons ()
	{
		ActionListener cancelListener = new CancelListener();
		addMenuButton(LABEL_CANCEL, cancelListener);
	}

	@Override
	public void addSelectionListener (SelectionListener selectionListener)
	{
		this.selectionListener = selectionListener;
	}

	@Override
	public void updateList()
	{
		super.updateList();
	}

	class SelectListener implements ActionListener
	{

		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			if (selectionListener != null) {
				ContactButton cb = (ContactButton) actionEvent.getSource();
				selectionListener.onSelect(cb.getContact());
			}
		}
	}

	class CancelListener implements ActionListener
	{

		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			if (selectionListener != null) {
				selectionListener.onCancel();
			}
		}
	}
}
