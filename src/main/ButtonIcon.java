package main;

import java.awt.Dimension;

import javax.swing.*;

@SuppressWarnings("serial")
public class ButtonIcon extends JButton {
	
	private String imagePath = "ressourcesSystem/";
	
	public ButtonIcon(String way, String appName)
	{
		String path;
		String projectPath;
		
		projectPath = System.getProperty("user.dir");
		projectPath.replace('\\', '/');
		
		path = projectPath + "/" + imagePath;
		
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		
		setIcon(new ImageIcon(imagePath + way));
        

	}
}

