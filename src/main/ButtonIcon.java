package main;

import java.awt.Dimension;

import javax.swing.*;

public class ButtonIcon extends JButton {
		private String imagePath = "C:/Users/Louise/git/SimPhone/ressourcesSystem/";
	
	public ButtonIcon(String way, String appName) {
	
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        
        setIcon(new ImageIcon(imagePath + way));
        

	}
	
}

