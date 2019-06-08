package gallery;

import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainView extends ListView
{

	MainView (ArrayList<GalleryImage> imageList, int size, ActionListener imageListener, ActionListener addListener)
	{
		super(imageList, size, imageListener);

		addMenuButton("Ajouter", addListener);
	}

}
