package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
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
		
		
		JLabel timeLabel = new JLabel("");
		add(timeLabel, BorderLayout.EAST);

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
