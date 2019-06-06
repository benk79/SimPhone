package main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


public class OS {
	
	public static final String[] appNames = {
			"helloworld.HelloWorldApp",
			"tictactoe.TicTacToe",
			"calendar.calendarApp",
			"contact.ContactApp",
			"gallery.GalleryApp"
		};
	
	private Phone phone;
	
	private JPanel  appCards;

	private int appScreenWidth; 


	private int appScreenHeight; 
	
	private ArrayList<Application> applications;

	public OS (Phone p)
	{
		phone        = p;
		appCards     = new JPanel();
		applications = new ArrayList<Application>();
		loadedAppsListeners = new ArrayList<ActionListener>();

		
		/*
		 * Screen containers
		 */
		addScreenContainers();


		/*
		 * Launcher
		 */
		HomeScreen homeScreen = new HomeScreen(this);
		
		
		appCards.add(homeScreen, "home");
		
		//
		AppBouttonListener abl = getAppBouttonListener("home");		
		phone.mainButton.addActionListener(abl);

		
		/*
		 *  Load applications
		 *  
		 *  this will check if applications listed are of the right type, 
		 *  and load those who are correct from Phone point of view
		 */
		for (String app: appNames) {			
			loadApp(app);
		}

		ActionEvent loadedApps = new ActionEvent(this,
			ActionEvent.ACTION_PERFORMED, "Loaded applications");

		for (ActionListener listener : loadedAppsListeners) {
			listener.actionPerformed(loadedApps);
		}


		//
		homeScreen.addApps();		
		showAppScreen("home");		
	}

	
	/**
	 * @return the appScreenWidth
	 */
	public int getAppScreenWidth() {
		return appScreenWidth;
	}

	
	/**
	 * @return the appScreenHeight
	 */
	public int getAppScreenHeight() {
		return appScreenHeight;
	}

	/**********************************************************************
	 * 
	 *   Phone screen containers (notification bar and application
	 *   panel container)
	 *   
	 *********************************************************************/
	

	
	private void addScreenContainers ()
	{
		appScreenWidth = Config.SCREEN_WIDTH;
		appScreenHeight = Config.SCREEN_HEIGHT - Config.NOTIFICATION_BAR_HEIGHT;
		
		/*
		 * Notification bar
		 */
		NotificationBar notificationBar = new NotificationBar();
		
		phone.screen.add(notificationBar, BorderLayout.NORTH);

		
		/*
		 * JPanel with CardLayout for applications
		 */
		Dimension dimension = new Dimension(
				appScreenWidth, 
				appScreenHeight
		);

		appCards.setLayout(new CardLayout());
		appCards.setPreferredSize(dimension);
		appCards.setBackground(Color.CYAN);
		
		phone.screen.add(appCards, BorderLayout.SOUTH);
		
	}	
		
	
	private void showAppScreen(String screenName)
	{
		CardLayout cl = (CardLayout)(appCards.getLayout());
	        cl.show(appCards, screenName);
	}
	
	

	
	/**********************************************************************
	 * 
	 *   Applications
	 *   
	 *********************************************************************/

	
	private void loadApp (String appName)	
	{
		Application app;
		
		try {
			app = (Application) Class.forName(appName).newInstance();

			app.setOs(this);

			applications.add(app);
			appCards.add(app.screen, getAppScreenName(app));
			
			app.onInit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<ActionListener> loadedAppsListeners;

	public void addLoadedAppsListener (ActionListener loadedAppsListener)
	{
		loadedAppsListeners.add(loadedAppsListener);
	}

	public Application getLoadedApp (Class c) throws ClassNotFoundException
	{
		for (Application a : applications) {
			if (a.getClass() == c) {
				return a;
			}
		}

		throw new ClassNotFoundException("Class not Found");
	}
	
	
	public String getAppScreenName (Application app)
	{
		return "app." + app.getName();
	}
	
	
	public ArrayList<Application> getApps()
	{
		return applications;
	}	
	
	

	
	/**********************************************************************
	 * 
	 *   Application button listener
	 *   
	 *********************************************************************/
	
	
	public AppBouttonListener getAppBouttonListener(String screenName)
	{
		return new AppBouttonListener(screenName);
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
	
	public class AppLauncher implements AppListener
	{
		
	}

}
