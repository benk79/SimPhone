package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class NotificationBar extends JPanel {
	
	public NotificationBar ()
	{
		Dimension dimension = new Dimension(
				Config.SCREEN_WIDTH, 
				Config.NOTIFICATION_BAR_HEIGHT
			);

		setLayout(new BorderLayout());
		setPreferredSize(dimension);
		setBackground(Color.black);
		
		ImageIcon swiss = new ImageIcon("ressourcesSystem/swisscom.png");
		JLabel img = new JLabel(swiss);
		add(img, BorderLayout.WEST);
		
		ImageIcon battery = new ImageIcon("ressourcesSystem/battery.png");
		JLabel img2 = new JLabel(battery);
		add(img2, BorderLayout.EAST);
		
		JLabel timeLabel = new JLabel("", SwingConstants.CENTER);
		add(timeLabel, BorderLayout.CENTER);

		timeLabel.setForeground(Color.white);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");		
		
		ActionListener taskPerformer = new ActionListener() {
			      public void actionPerformed(ActionEvent evt) {
				      updateTime(timeLabel, dtf);
			      }
			  };
		
		new Timer(1000, taskPerformer).start();
			
		
	}
	
	private void updateTime (JLabel label, DateTimeFormatter dtf)
	{
		LocalDateTime now = LocalDateTime.now();  
		label.setText(dtf.format(now));
	}
}
