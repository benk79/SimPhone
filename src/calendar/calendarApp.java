package calendar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Application;

public class calendarApp extends Application  {

	public calendarApp() {
		super("Calendrier");

		Calendar cal = new GregorianCalendar();

		int annee = cal.get(Calendar.YEAR);
		int moiscourant = cal.get(Calendar.MONTH);

		int nombrejoursmois;
		String moisecrit=null;
		
		System.out.println(annee);

		boolean bisex = false;

		if (annee % 4 == 0 && annee % 100 != 0) {
			bisex = true;
		}

		if (annee % 400 == 0) {
			bisex = true;
		}

		moiscourant = moiscourant + 1;

		switch (moiscourant) {

		case 4:
		case 6:
		case 9:
		case 11:

			nombrejoursmois = 30;
			break;
		case 2:
			if (bisex == true) {
				nombrejoursmois = 29;
			} else {
				nombrejoursmois = 28;
			}
		default :
			nombrejoursmois = 31;
		}

		

		int jourdelasemaine = cal.get(Calendar.DAY_OF_WEEK);
		jourdelasemaine = jourdelasemaine - 1;
		String jourecrit = null;

		switch (jourdelasemaine) {

		case 1: {
			jourecrit = "lundi";
			break;
		}

		case 2: {
			jourecrit = "mardi";
			break;
		}

		case 3: {
			jourecrit = "mecredi";
			break;
		}

		case 4: {
			jourecrit = "jeudi";
			break;
		}

		case 5: {
			jourecrit = "vendredi";
			break;
		}

		case 6: {
			jourecrit = "samedi";
			break;
		}

		case 7: {
			jourecrit = "dimanche";
			break;
		}

		}

		switch (moiscourant) {
		case 1: {
			moisecrit = "janvier";
			break;
		}
		case 2: {
			moisecrit = "fevrier";
			break;
		}

		case 3: {
			moisecrit = "mars";
			break;
		}

		case 4: {
			moisecrit = "avril";
			break;
		}

		case 5: {
			moisecrit = "mai";
			break;
		}

		case 6: {
			moisecrit = "juin";
			break;
		}

		case 7: {
			moisecrit = "juillet";
			break;
		}

		case 8: {
			moisecrit = "aout";
			break;
		}

		case 9: {
			moisecrit = "septembre";
			break;
		}

		case 10: {
			moisecrit = "octobre";
			break;
		}

		case 11: {
			moisecrit = "novembre";
			break;
		}

		case 12: {
			moisecrit = "decembre";
		}
			break;
		}
		
		
		//
		JLabel titleLabel = new JLabel("<html><h1>" + getName() + "</h1></html>");

		screen.add(titleLabel);
		screen.setLayout(new BorderLayout());

		JButton arriere = new JButton("ARRIERE");
		JLabel JLabelmoiscourant = new JLabel(moisecrit);
		JLabel JLabelanneecourante = new JLabel(String.valueOf(annee));
		JButton avant = new JButton("AVANT");

		
		
		 
		//int nbJours = 31;

		
		JLabel[] labelJours = new JLabel[nombrejoursmois];

		
		for(int i=0;i<nombrejoursmois;i++) {
		labelJours[i]= new JLabel((Integer.toString(i+1))); 	
		}
		
		

		JLabel[] addJours = new JLabel[31];
		
		
		for (int i = 0; i < 31; i++) {

			addJours[i] = new JLabel(Integer.toString(i));

		}

		JPanel superieur1 = new JPanel();
		superieur1.setLayout(new BorderLayout());
		superieur1.add(arriere, BorderLayout.WEST);
		superieur1.add(avant, BorderLayout.EAST);
		//superieur1.add(JLabelmoiscourant, BorderLayout.CENTER);
		//superieur1.add(JLabelanneecourante, BorderLayout.SOUTH);
		
		JPanel superieur2= new JPanel();
		superieur2.setLayout(new BorderLayout());
		superieur2.add(JLabelmoiscourant, BorderLayout.WEST);
		superieur2.add(JLabelanneecourante, BorderLayout.EAST);
		
		JPanel central = new JPanel();
		central.setLayout(new GridLayout(6, 7, 5, 10));

		
		/*
		 * Add empty labels to fill home screen grid layout
		 */
		
		for(int i=0;i<nombrejoursmois;i++) {
			
			central.add(labelJours[i]);
			
		}
		
		
		int gridEmptyPlaces = 6 * 7 - nombrejoursmois;

		for (int i = 0; i < gridEmptyPlaces; i++) {
			central.add(new JLabel(""));
		}

	//	superieur1.addComponentListener(this) ;
		
		
		
		
		screen.add(superieur1, BorderLayout.NORTH);
		screen.add(superieur2, BorderLayout.CENTER);
		screen.add(central, BorderLayout.SOUTH);

	}

}
