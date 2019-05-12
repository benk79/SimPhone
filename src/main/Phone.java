package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import javax.swing.Timer; 
//import java.util.TimerTask; 

@SuppressWarnings("serial")
public class Phone extends JPanel {
	
	public static final String[] appNames = {
		"helloworld.HelloWorldApp",
		"tictactoe.TicTacToe",
		"calendar.calendarApp"
	};

	public static final int PHONE_SCREEN_WIDTH = 450;
	public static final int PHONE_SCREEN_HEIGHT = 800;
	public static final int PHONE_BORDER = 10;
	public static final int PHONE_BUTTON_BORDER = 40;
	
	public static final int HOME_GRID_COLS = 2;
	public static final int HOME_GRID_ROWS = 6;
	
	
	// private JPanel currentScreen;
	
	
	private ArrayList<Application> applications = new ArrayList<Application>();

	private JButton mainButton = new JButton("Home");
	private JPanel  screenPanel = new JPanel(new BorderLayout());

	private JPanel  appCards = new JPanel();
	
	
	
	private JPanel  homeScreen     = new JPanel();
	private JPanel notificationBar = new JPanel(new BorderLayout());
	
		
	public Phone ()
	{
		super(new BorderLayout());

		/*
		 * Main device parts
		 */
		addDeviceBorders();
		addDeviceScreen();

		
		/*
		 * Screen containers and home screen
		 * 
		 * Initialize the main screen element parts
		 * and launch the home screen
		 */
		addNotificationBar();
		addAppScreenContainer();
		addHomeScreen();

		
		/*
		 *  Load applications
		 *  
		 *  this will check if applications listed are of the right type, 
		 *  and load those who are correct from Phone point of view
		 */
		for (String app: appNames) {			
			loadApp(app);
		}

		//
		addApps();
		
		showAppScreen("home");
		
	}
	

	private void addDeviceBorders ()
	{
		/*
		 * Define variables
		 */
		JPanel topBorder    = new JPanel();
		JPanel leftBorder   = new JPanel();
		JPanel bottomBorder = new JPanel();	
		JPanel rightBorder  = new JPanel();

		//
		int pWidth  = PHONE_SCREEN_WIDTH;
		int pHeight = PHONE_SCREEN_HEIGHT;
		int border  = PHONE_BORDER;


		/*
		 * Add all device borders
		 */
		addDeviceBorderPanel(topBorder, border * 2 + pWidth, border, BorderLayout.NORTH);
		addDeviceBorderPanel(leftBorder, border, pHeight, BorderLayout.WEST);
		addDeviceBorderPanel(bottomBorder, border * 2 + pWidth, PHONE_BUTTON_BORDER, BorderLayout.SOUTH);
		addDeviceBorderPanel(rightBorder, border, pHeight, BorderLayout.EAST);	

		
		/*
		 * Add  main button to bottom border
		 */
		AppBouttonListener abl = new AppBouttonListener("home");		
		mainButton.addActionListener(abl);
		
		bottomBorder.add(mainButton);
	}
	
	

	private void addDeviceBorderPanel (JPanel panel, int w, int h, String place)
	{
		Dimension dimension = new Dimension(w, h);
		
		panel.setPreferredSize(dimension);
		panel.setBackground(Color.red);

		add(panel, place);		
	}
	


	private void addDeviceScreen ()
	{
		Dimension screenSize = new Dimension(
			PHONE_SCREEN_WIDTH, 
			PHONE_SCREEN_HEIGHT
		);
		
		screenPanel.setPreferredSize(screenSize);
		screenPanel.setBackground(Color.blue);

		add(screenPanel);		
	}
	
	
	private void loadApp (String appName)	
	{
		Application app;
		
		try {
			app = (Application) Class.forName(appName).newInstance();
			applications.add(app);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void addNotificationBar ()
	{
		Dimension dimension = new Dimension(
			PHONE_SCREEN_WIDTH, 30
		);

		notificationBar.setLayout(new BorderLayout());
		notificationBar.setPreferredSize(dimension);
		notificationBar.setBackground(Color.black);
		
		screenPanel.add(notificationBar, BorderLayout.NORTH);
		
		JLabel timeLabel = new JLabel("");
		notificationBar.add(timeLabel, BorderLayout.EAST);

		timeLabel.setForeground(Color.white);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");		
		
		ActionListener taskPerformer = new ActionListener() {
			      public void actionPerformed(ActionEvent evt) {
				      updateTime(timeLabel, dtf);
			      }
			  };
		
		new Timer(1000, taskPerformer).start();
		// timer.
		
			
		
	}
	
	private void updateTime (JLabel label, DateTimeFormatter dtf)
	{
		LocalDateTime now = LocalDateTime.now();  
		label.setText(dtf.format(now));
	}
	
	

	
	private void addAppScreenContainer ()
	{
		Dimension dimension = new Dimension(
			PHONE_SCREEN_WIDTH, 770
		);

		appCards.setLayout(new CardLayout());
		appCards.setPreferredSize(dimension);
		appCards.setBackground(Color.CYAN);
		
		screenPanel.add(appCards, BorderLayout.SOUTH);		
	}	
	
	
	private void addHomeScreen ()
	{
		GridLayout homeScreenLayout = new GridLayout(
				HOME_GRID_ROWS, 
				HOME_GRID_COLS, 
				10, 
				10);

		//
		homeScreen.setLayout(homeScreenLayout);
		homeScreen.setPreferredSize(new Dimension(PHONE_SCREEN_WIDTH, 770));
		homeScreen.setBackground(Color.green);

		//
		appCards.add(homeScreen, "home");	
	}


	
	private void addApps ()
	{		
		
		for (Application app: applications) {

			String screenName = "app." + app.getName();
			JButton runAppButton = new JButton(app.getName());
			
			AppBouttonListener abl = new AppBouttonListener(screenName);
			runAppButton.addActionListener(abl);
			
			homeScreen.add(runAppButton);
			appCards.add(app.screen, screenName);

		}


		/*
		 * Add empty labels to fill home screen grid layout
		 */
		int gridEmptyPlaces = HOME_GRID_COLS * HOME_GRID_ROWS - applications.size();
		
		for (int i = 0; i < gridEmptyPlaces; i++) {
			homeScreen.add(new JLabel(""));
		}
		
	}

	
	public void showAppScreen(String screenName)
	{
		CardLayout cl = (CardLayout)(appCards.getLayout());
	        cl.show(appCards, screenName);
	}

	
	public class AppBouttonListener implements ActionListener
	{
		private String screenName;
		
		public AppBouttonListener (String screenName)
		{
			this.screenName = screenName;
		}
		
		@Override
		public void actionPerformed (ActionEvent e)
		{
			showAppScreen(screenName);
		}
	
	} 		

}


