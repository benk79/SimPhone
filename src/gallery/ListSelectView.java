package gallery;

import contact.ContactButton;
import main.SelectionListener;
import main.SeletionPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListSelectView extends ListView implements SeletionPanel
{
	private SelectionListener selectionListener;

	/**
	 * Listener for the select contact button
	 */
	private ActionListener selectListener;


	public ListSelectView (ArrayList<GalleryImage> imageList)
	{
		super(imageList);
		selectListener = new SelectListener();
		setImageListener(selectListener);

		initListView();

		ActionListener cancelListener = new CancelListener();
		addMenuButton("Annuler", cancelListener);

	}

	@Override
	public void addSelectionListener (SelectionListener selectionListener)
	{
		this.selectionListener = selectionListener;
	}

	class SelectListener implements ActionListener
	{

		@Override
		public void actionPerformed (ActionEvent actionEvent)
		{
			if (selectionListener != null) {
				ImageButton cb = (ImageButton) actionEvent.getSource();
				selectionListener.onSelect(cb.getImage());
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
