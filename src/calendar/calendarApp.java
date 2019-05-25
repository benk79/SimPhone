package calendar;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.font.TextAttribute;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Application;

public class calendarApp extends Application implements ActionListener {

	protected int moiscourrant;
	private JButton avant = new JButton("precedant");
	private JButton apres = new JButton("suivant");

	//	private JPanel   m superieur1;
	
	
	public calendarApp() {
		super("Calendrier");

		// donnée sur les chiffres

		Calendar cal = new GregorianCalendar();
		int annee = cal.get(Calendar.YEAR);
		int moiscourant = cal.get(Calendar.MONTH);

		int moisavant = cal.get(Calendar.MONTH);
		moisavant = moisavant - 1;

		int moisapres = cal.get(Calendar.MONTH);
		moisapres = moisapres + 1;

		int jourdelasemaine = cal.get(Calendar.DAY_OF_WEEK);
		int datedujour = cal.get(Calendar.DAY_OF_MONTH);
		int nombrejoursmois;
		String moisecrit = null;
		boolean bisex = false;
		bisex = bisextile();
		nombrejoursmois = direnombrejour(bisex, moiscourant);
		direlejourdelasemaine(jourdelasemaine);
		moisecrit = direlenomdumois(moiscourant);

		// jpanel superieur

		screen.setLayout(new BorderLayout());

		// on annonce les elements

		String moisavantecrit = direlenomdumois(moisavant);
		String moisapresecrit = direlenomdumois(moisapres);

		JLabel JLabelmoiscourant = new JLabel(moisecrit);
		JLabel JLabelanneecourante = new JLabel(String.valueOf(annee));

		JLabelmoiscourant.setForeground(Color.blue);
		JLabelanneecourante.setForeground(Color.blue);
		JPanel superieur1 = new JPanel();

		// on les ajoute

		superieur1.setLayout(new FlowLayout());

		superieur1.add(avant);
		superieur1.add(JLabelmoiscourant);
		superieur1.add(JLabelanneecourante);

		superieur1.add(apres);

		apres.addActionListener(this);

		avant.addActionListener(this);

		char tablettre[] = new char[7];
		int chiffretab[] = new int[7];

		for (int i = 0; i < 7; i++) {

			chiffretab[i] = tabpourlettre(i);

		}

		for (int i = 0; i < 7; i++) {

			tablettre[i] = choisirlettre(chiffretab[i]);

		}

		
		//jpanel intermediaire
		// creer tab jsemaine
		// JLabel js1 = new JLabel(" puisse dieu nous venir en aide");
		JLabel[] jsemaine = new JLabel[7];

		for (int i = 0; i < 7; i++) {
			jsemaine[i] = new JLabel(String.valueOf(tablettre[i]));

			jsemaine[i].setForeground(Color.MAGENTA);
		}

		// creer le jpanel
		JPanel superieur2 = new JPanel();
		superieur2.setLayout(new GridLayout());

		//on ajoute jsemaine
		//	superieur2.add(js1);

		for (int i = 0; i < 7; i++) {

		// (Jsemaine[i] == d) {
		// jsemaine[i].setForeground(Color.green);
		// }

			superieur2.add(jsemaine[i]);

		}

		// jpanel du bas 
		// creer tab labeljour
		JLabel[] labelJours = new JLabel[nombrejoursmois];
		for (int i = 0; i < nombrejoursmois; i++) {
			labelJours[i] = new JLabel((Integer.toString(i + 1)));
		}
		// creer le panel
		JPanel central = new JPanel();
		central.setLayout(new GridLayout(6, 7, 5, 10));

		// on ajoute le tab dans le panel
		for (int i = 0; i < nombrejoursmois; i++) {
			if (i == datedujour) {
				labelJours[i].setForeground(Color.green);
			}
			central.add(labelJours[i]);
		}

		// gerer espace vide
		int gridEmptyPlaces = 6 * 7 - nombrejoursmois;
		for (int i = 0; i < gridEmptyPlaces; i++) {
			central.add(new JLabel(""));
		}

		// mettre ensemble les jpanel

		screen.add(superieur1, BorderLayout.NORTH);
		screen.add(superieur2, BorderLayout.CENTER);
		screen.add(central, BorderLayout.SOUTH);

	}

	public static boolean bisextile() {

		Calendar cal = new GregorianCalendar();

		int annee = cal.get(Calendar.YEAR);
		boolean bisex = false;

		if (annee % 4 == 0 && annee % 100 != 0) {
			bisex = true;
		}

		if (annee % 400 == 0) {
			bisex = true;
		}

		return bisex;
	}

	public static int direnombrejour(boolean bisex, int moiscourant) {

		moiscourant = moiscourant + 1;

		int nombrejoursmois;
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
		default:
			nombrejoursmois = 31;

		}
		return nombrejoursmois;

	}

	public static char[] direlejourdelasemaine(int jourdelasemaine) {

		char[] semaine = new char[7];

		for (int i = 0; i < semaine.length; i++) {

		}

		return semaine;
	}

	public static String direlenomdumois(int moiscourant) {

		String moisecrit = null;
		switch (moiscourant) {
		case 1: return "janvier";
		case 2: return "fevrier";
		case 3: return "mars";
		case 4: return "avril";
		case 5: return "mai";
		case 6: return "juin";
		case 7: return "juillet";
		case 8: return "aout";
		case 9: return "septembre";
		case 10:return  "octobre";
		case 11:return "novembre";
		case 12:return "decembre";
		}
		return moisecrit;

	}

	public static int augmentermois(int moiscourant) {
		moiscourant = moiscourant;
		return moiscourant;
	}

	public static char choisirlettre(int numero) {
		char lettre = 0;
		switch (numero) {
		case 0: return 'L';
		case 1: return'M';
		case 2:	return 'M';
		case 3:	return 'J';
		case 4: return 'V';
		case 5: return 'S';
		case 6:return 'D';
		}
		return lettre;
	}

	public static int tabpourlettre(int numero) {

		Calendar calendar = new GregorianCalendar();

		int cannee = calendar.get(Calendar.YEAR);
		int cmois = calendar.get(Calendar.MONTH);

		// calendar = new GregorianCalendar(1913,10,23);

		calendar = new GregorianCalendar(cannee, cmois, 0);

		// System.out.println(sdf.format(calendar.getTime()));

		int cjourdelasemaine = calendar.get(Calendar.DAY_OF_WEEK);
		int[] csemaine = new int[7];

		cjourdelasemaine = cjourdelasemaine - 1;
		for (int i = 0; i < csemaine.length; i++) {

			if (cjourdelasemaine > 6) {
				cjourdelasemaine = cjourdelasemaine - 7;
			}

			csemaine[i] = cjourdelasemaine;
			cjourdelasemaine = cjourdelasemaine + 1;
		}

		return csemaine[numero];

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("click avant");

		if (e.getSource() == avant) {
			moiscourrant = moiscourrant - 1;
		}
		if (e.getSource() == apres) {
			moiscourrant = moiscourrant + 1;
		}

	}
	public static void raffraichir(JPanel superieur1,JPanel superieur2, JPanel central) {
		
		screen.remove(superieur1);
		screen.remove(superieur2);
		screen.remove(central);
	}
}
