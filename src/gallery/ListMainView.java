package gallery;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListMainView extends ListView
{
	public ListMainView (ArrayList<GalleryImage> imageList, int size, ActionListener addListener)
	{
		super(imageList);

		setImageListener(addListener);

		initListView();

		addMenuButton("Ajouter", addListener);
	}
}
