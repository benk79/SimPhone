package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class Phone extends JPanel {

	public JPanel  screen;
	public ButtonIcon mainButton;
	
	private boolean powerState;
	
	private OS os;
		
	public Phone ()
	{
		super(new BorderLayout());

		//
		addDeviceBorders();
		addDeviceScreen();

		//
		new OS(this);
	}
	
	
	/*
	public void powerOnOff ()
	{
		if (os instanceof OS)
			powerOff();
		else
			powerOn();
	}
	
	private void powerOn()
	{
		os = new OS(this);
	}
	
	
	private void powerOff()
	{
		os = null;
	}
	*/

	/**********************************************************************
	 * 
	 *   General device elements
	 *   
	 *********************************************************************/
	
	private void addDeviceBorders ()
	{

		/*
		 * Define variables
		 */
		
		JPanel topBorder    = new JPanel();
		JPanel leftBorder   = new JPanel();
		JPanel bottomBorder = new JPanel();	
		JPanel rightBorder  = new JPanel();


		/*
		 * Add main button and device borders
		 */

		//
		int pWidth  = Config.SCREEN_WIDTH;
		int pHeight = Config.SCREEN_HEIGHT;
		int border  = Config.BORDER;


		//
		addDeviceBorderPanel(
				topBorder, 
				border * 2 + pWidth,
				border, 
				BorderLayout.NORTH);
		
		addDeviceBorderPanel(
				leftBorder, 
				border, 
				pHeight, 
				BorderLayout.WEST);
		
		addDeviceBorderPanel(
				bottomBorder, 
				border * 2 + pWidth, 
				Config.BUTTON_BORDER , 
				BorderLayout.SOUTH);
		
		addDeviceBorderPanel(
				rightBorder, 
				border, 
				pHeight, 
				BorderLayout.EAST);


		//
		mainButton = new ButtonIcon("homeButton.png", "home");
		bottomBorder.add(mainButton);		

	}
	
	

	private void addDeviceBorderPanel (JPanel panel, int w, int h, String place)
	{
		Dimension dimension = new Dimension(w, h);
		
		panel.setPreferredSize(dimension);
		panel.setBackground(Color.black);

		add(panel, place);		
	}
	


	private void addDeviceScreen ()
	{
		Dimension screenSize;

		//
		screen = new JPanel(new BorderLayout());
		
		screenSize = new Dimension(
			Config.SCREEN_WIDTH, 
			Config.SCREEN_HEIGHT
		);

		//
		screen.setPreferredSize(screenSize);
		screen.setBackground(Color.blue);

		add(screen);	
	}
	

}


