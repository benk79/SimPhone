package calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class QuelJour {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd ");

		Calendar calendar = new GregorianCalendar();

		int cannee = calendar.get(Calendar.YEAR);
		int cmois = calendar.get(Calendar.MONTH);

	//	System.out.println(cmois);
		//System.out.println(cannee);

		// calendar = new GregorianCalendar(1913,10,23);

		calendar = new GregorianCalendar(cannee, cmois, 0);

	//	System.out.println(sdf.format(calendar.getTime()));

		int cjourdelasemaine = calendar.get(Calendar.DAY_OF_WEEK);
		int csemaine[] = new int[7];
	//	System.out.println(cjourdelasemaine);

		for (int i = 0; i < csemaine.length; i++) {

			if (cjourdelasemaine > 7) {
				cjourdelasemaine = cjourdelasemaine - 7;
			}

			csemaine[i] = cjourdelasemaine;
			cjourdelasemaine = cjourdelasemaine + 1;
		}

		for (int i = 0; i < csemaine.length; i++) {

			System.out.println(csemaine[i]);

		}

		char csemainelettre[]= new char[7];
		for (int i = 0; i < csemaine.length; i++) {

			csemainelettre[i]=choisirlettre(csemaine[i]);

		}
		
		for (int i = 0; i < csemaine.length; i++) {

			System.out.println(csemainelettre[i]);

		}
		
			//int nimportequoi=4;	
			//char lettretest=choisirlettre(nimportequoi);
			//System.out.println(lettretest);
		}

		
	

	public static char choisirlettre(int numero) {
		numero=numero-1;
		char lettre = 0;

		switch (numero) {

		case 0:
			lettre = 'L';
			break;
		case 1:
			lettre = 'M';
			break;
		case 2:
			lettre = 'M';
			break;
		case 3:
			lettre = 'J';
			break;
		case 4:
			lettre = 'V';
			break;
		case 5:
			lettre = 'S';
			break;
		case 6:
			lettre = 'D';
			break;

		}

		return lettre;
	}

}
