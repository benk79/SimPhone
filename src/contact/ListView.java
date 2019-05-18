package contact;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ListView extends JPanel {
	

	public ListView (Contact[] contactList)
	{
		JPanel menu = new JPanel();
		JPanel list = new JPanel();
		
		JButton addBoutton = new JButton("New contact");
		
		
		setLayout(new BorderLayout());
		
		add(list);
		add(menu, BorderLayout.SOUTH);
		
		list.add(new JLabel("liste"));
		menu.add(new JLabel("menu"));
		
	}
	
	/*class AddBouttonListener implements ActionListener
	{
		
	}*/
}
