package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.OS.AppBouttonListener;

@SuppressWarnings("serial")
public class HomeScreen extends JPanel {
	
	private OS os;


	public HomeScreen (OS opSys)
	{
		os = opSys;
		
		GridLayout homeScreenLayout = new GridLayout(
				Config.HOME_GRID_ROWS, 
				Config.HOME_GRID_COLS, 
				10, 
				10);

		//
		int sw = Config.SCREEN_WIDTH;
		int sh = Config.SCREEN_HEIGHT - Config.NOTIFICATION_BAR_HEIGHT;
		
		setLayout(homeScreenLayout);
		setPreferredSize(new Dimension(sw, sh));
		setBackground(Color.black);
	}
	
	
	public void addApps() 
	{
		String appName;
		String appIcon;
		String screenName;
		
		JButton runAppButton;
		AppBouttonListener abl;
		
		ArrayList<Application> applications = os.getApps();
		
		for (Application app: applications) {

			appName = app.getName();
			appIcon = app.getIcon();
			
			screenName = os.getAppScreenName(app);
			
			runAppButton = new ButtonIcon(appIcon, appName);
			abl = os.getAppBouttonListener(screenName);
			
			runAppButton.addActionListener(abl);
			
			add(runAppButton);

		}


		/*
		 * Add empty labels to fill home screen grid layout
		 */
		int gridEmptyPlaces = Config.HOME_GRID_COLS * Config.HOME_GRID_ROWS - applications.size();
		
		for (int i = 0; i < gridEmptyPlaces; i++) {
			add(new JLabel(""));
		}
	}
}
