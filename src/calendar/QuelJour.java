package calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class QuelJour {

	public static void main(String[] args) {

		Calendar cal = new GregorianCalendar();

		int annee = cal.get(Calendar.YEAR);
		int mois = cal.get(Calendar.MONTH);

		int jourdelasemaine = cal.get(Calendar.DAY_OF_WEEK);
		jourdelasemaine = jourdelasemaine - 1;
		String jourecrit = null;
		String moisecrit = null;

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

		switch (mois) {
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

		int nombrejoursmois;

		boolean bisex = false;

		if (annee % 4 == 0 && annee % 100 != 0) {
			bisex = true;
		}

		if (annee % 400 == 0) {
			bisex = true;
		}

		mois = mois + 1;

		switch (mois) {

		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:

			nombrejoursmois = 31;
			break;

		case 4:
		case 6:
		case 9:
		case 11:

			nombrejoursmois = 30;
			break;
		}

		if (mois == 2 && bisex == true) {
			nombrejoursmois = 29;
		} else {
			nombrejoursmois = 28;
		}

	}

}
