package contact;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Application;

public class ContactApp extends Application {
	
	public static final String LIST_VIEW = "LIST";
	
	public static final String DETAIL_VIEW = "DETAIL";
	

	private Contact[] contactList;
	
	private CardLayout layout;

	private JPanel listView;
	
	private JPanel detailView;
	
	public ContactApp()
	{
		super("Contacts", "");

		//
		contactList = new Contact[200];
		
	}
	
	
	public void onInit()
	{

		layout = new CardLayout();
		
		screen.setLayout(layout);
		
		
		int w = os.getAppScreenWidth();
		int h = os.getAppScreenHeight();
		
		Dimension dim = new Dimension(w, h);
		screen.setPreferredSize(dim);
		
		listView = new ListView(contactList);
		//listView.add(new JLabel("vue liste"));
		listView.setBackground(Color.BLUE);
		detailView = new JPanel();
		
		screen.add(listView, LIST_VIEW);
		screen.add(detailView, DETAIL_VIEW);
		
		
		showView(LIST_VIEW);
		
	}
	
	
	private void showView(String viewName)
	{
	        layout.show(screen, viewName);	
	}
	
	
}
