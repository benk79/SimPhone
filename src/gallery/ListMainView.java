package gallery;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListMainView extends ListView
{
	public ListMainView (ArrayList<GalleryImage> imageList, int size, ActionListener imageListener, ActionListener addListener)
	{
		super(imageList);

		setImageListener(imageListener);

		initListView();

		addMenuButton("add.png", addListener);
	}
}
