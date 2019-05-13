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
			"calendar.calendarApp"
		};
	
	private Phone phone;
	
	private JPanel  appCards;
	
	private ArrayList<Application> applications;
	
	public OS (Phone p)
	{
		phone        = p;
		appCards     = new JPanel();
		applications = new ArrayList<Application>();
		
		
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

		//
		homeScreen.addApps();		
		showAppScreen("home");		
	}

	/**********************************************************************
	 * 
	 *   Phone screen containers (notification bar and application
	 *   panel container)
	 *   
	 *********************************************************************/
	

	
	private void addScreenContainers ()
	{
		/*
		 * Notification bar
		 */
		NotificationBar notificationBar = new NotificationBar();
		
		phone.screen.add(notificationBar, BorderLayout.NORTH);

		
		/*
		 * JPanel with CardLayout for applications
		 */
		Dimension dimension = new Dimension(
			Config.SCREEN_WIDTH, 770
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
			applications.add(app);
			appCards.add(app.screen, getAppScreenName(app));	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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

}
