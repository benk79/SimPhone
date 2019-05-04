package main;

import javax.swing.JPanel;


/**
 * @author Benjamin Keller, Louise Bretz
 * 
 * Application abstract class. Every application inheriting from this
 * one will be runnable from home screen with a button
 */
public abstract class Application
{

	/**
	 * Main application panel which will be showed when clicking on 
	 * application button from home screen
	 */
	protected JPanel screen = new JPanel();
	
	
	private String name;
	private String icon;
	
	
	public Application (String name)
	{
		this.name = name;
		
		screen = new JPanel();			
	}

	
	/**
	 * @return Image name as application icon
	 */
	public String getIcon()
	{
		return icon;
	}


	/**
	 * @return Name of the application
	 */
	public String getName() {
		return name;
	}

}
