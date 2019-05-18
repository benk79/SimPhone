package contact;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ViewContainer extends JPanel {

	private CardLayout layout;
	
	private JPanel listView;
	
	private JPanel detailView;
	
	
	public ViewContainer (int w, int h)
	{
		layout = new CardLayout();
		
		setLayout(layout);
		
		Dimension dim = new Dimension(w, h);
		setPreferredSize(dim);
	}
}
