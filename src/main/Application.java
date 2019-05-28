package main;

import javax.swing.JPanel;
import java.io.File;


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

	/**
	 * Reference to OS which launched the application
	 */
	protected OS os;

	/**
	 * Name of application which can be used when listing applications
	 */
	private String name;

	/**
	 * Icon to use in home screen as button to launch application
	 */
	private String icon;

	private String dataPath;

	/**
	 * @param name Name of application
	 * @param icon Icon of application
	 */
	public Application (String name, String icon)
	{
		this.name = name;
		this.icon = icon;
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
	
	public void setOs(OS os)
	{
		this.os = os;
	}
	
	public void onInit() {}

}
