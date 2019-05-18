package main;

import java.awt.Dimension;

import javax.swing.*;

public class ButtonIcon extends JButton {
	
	  
	
	public ButtonIcon(String way, String appName) {
	
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        
        setIcon(new ImageIcon(way));
        

	}
	
}

