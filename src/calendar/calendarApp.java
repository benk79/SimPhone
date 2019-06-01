package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import contact.Contact;
import contact.ContactApp;
import main.Application;

public class calendarApp extends Application implements ActionListener {

	private ContactApp contactApp;

	private LocalDate moiscourant;
	private JButton avant = new JButton("précédent");
	private JButton apres = new JButton("suivant");
	private JLabel jLabelmoiscourant;
	private JLabel[] jLabeljours;

	private JList<String> anniversaires;

	// private JPanel m superieur1;
	public calendarApp() {
		super("Calendrier", "calendrier.png");

		// jpanel superieur

		screen.setLayout(new BorderLayout());

		// on annonce les elements

		jLabelmoiscourant = new JLabel();
		jLabelmoiscourant.setForeground(Color.blue);
		JPanel superieur1 = new JPanel();

		// on les ajoute

		superieur1.setLayout(new FlowLayout());
		superieur1.add(avant);
		superieur1.add(jLabelmoiscourant);
		superieur1.add(apres);

		anniversaires = new JList<String>();

		// jpanel du bas
		// creer le panel
		JPanel calendrier = new JPanel();
		calendrier.setLayout(new GridLayout(7, 7, 5, 10));

		// on ajoute jsemaine
		for (int i = 0; i < 7; i++) {
			DayOfWeek joursemaine = DayOfWeek.of(i + 1);
			String joursemainetexte = joursemaine.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.FRENCH);
			JLabel jsemaine = new JLabel(joursemainetexte);
			jsemaine.setForeground(Color.MAGENTA);
			calendrier.add(jsemaine);
		}

		jLabeljours = new JLabel[6 * 7];
		// on ajoute le tab dans le panel
		for (int i = 0; i < 6 * 7; i++) {
			JLabel jLabeljour = new JLabel();
			jLabeljours[i] = jLabeljour;
			calendrier.add(jLabeljour);
		}

		screen.add(superieur1, BorderLayout.NORTH);
		screen.add(anniversaires, BorderLayout.CENTER);
		screen.add(calendrier, BorderLayout.SOUTH);

		apres.addActionListener(this);
		avant.addActionListener(this);

		setMoiscourant(LocalDate.now().withDayOfMonth(1));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// gestion d'evenement

		if (e.getSource() == avant) {

			setMoiscourant(moiscourant.minusMonths(1));
		}
		if (e.getSource() == apres) {
			setMoiscourant(moiscourant.plusMonths(1));
		}

	}

	void setMoiscourant(LocalDate nouveaumoiscourant) {
		moiscourant = nouveaumoiscourant;
		jLabelmoiscourant.setText(moiscourant.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH)));
		for (int i = 0; i < 6 * 7; i++) {
			jLabeljours[i].setText("");
			jLabeljours[i].setForeground(null);
		}

		int joursemainedu1er = moiscourant.getDayOfWeek().getValue();
		int joursdansmois = moiscourant.lengthOfMonth();
		for (int jourdumois = 1; jourdumois <= joursdansmois; jourdumois++) {
			int index = joursemainedu1er - 1 + jourdumois - 1;
			jLabeljours[index].setText(Integer.toString(jourdumois));
		}

		LocalDate aujourdhui = LocalDate.now();
		if (aujourdhui.withDayOfMonth(1).equals(moiscourant)) {
			int jourdumois = aujourdhui.getDayOfMonth();
			int index = joursemainedu1er - 1 + jourdumois - 1;
			jLabeljours[index].setForeground(Color.green);
		}

		afficheanniversaires();
	}

	public void onInit() {
		LoadedAppsListener loadedAppsListener = new LoadedAppsListener();
		os.addLoadedAppsListener(loadedAppsListener);
	}

	private class LoadedAppsListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			try {
				contactApp = (ContactApp) os.getLoadedApp(ContactApp.class);
				afficheanniversaires();
			} catch (ClassNotFoundException e) {
				System.out.println("Could not find ");
			}

		}
	}

	private void afficheanniversaires() {
		if (contactApp == null) {
			// les contacts ne sont pas encore chargés
			return;
		}

		// contactApp.getContacts();
		String[] annis = new String[1];
		annis[0] = "3. Bastien";
		anniversaires.setListData(annis);
	}

}