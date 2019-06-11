package tictactoe;

import javax.swing.*; 	  // Librairie graphique
import java.awt.*;		  // Composants Java 
import java.awt.event.*; // Permet d'utiliser les �v�nements li�s aux composants
import java.util.*;	  // Utilitaires, ici pour la g�n�ration de nombres al�atoires

public class PanelMain extends JPanel implements ActionListener {


		
	Paneau pan0,pan1,pan2,pan3,pan4,pan5,pan6,pan7,pan8;
	Paneau pans[] = { pan0,pan1,pan2,pan3,pan4,pan5,pan6,pan7,pan8 };
	Partie part;
	JPanel haut, bas;
	JButton but;
	
	GridLayout grid;
	
	JPanel panel = new JPanel(new BorderLayout());
				
	PanelMain() 
	
	{
		
		
		
		// Le tableau des Paneau est transmis au constructeur de Partie
		part = new Partie(pans);
		
		// Cr�ation de 2 JPanel pour la mise en forme de la fenetre
		haut = new JPanel();
		panel.add(haut);
		bas = new JPanel();
		panel.add(bas, BorderLayout.SOUTH);

		// Le gestionnaire de mise en forme du Panel haut est red�fini
		// en grille de 3 lignes, 3 colonnes avec 2 pixels entre chaques
		haut.setLayout(new GridLayout(3,3,2,2));

		// Cr�ation des 9 Paneau et ajout au Panel haut
		for (int  i=0 ; i<9 ; i++)
			{
			pans[i] = new  Paneau(part,i);
			haut.add(pans[i]);
			}
				
		// D�finition du bouton, ajout au Panel bas
		but = new JButton("Commencer");
		bas.add(but);

		// Ajout d'un ActionListener (�couteur d'action) sur le bouton
		// Cela va permettre d'intercepter le click par le biais de la 
		// m�thode actionPerformed (nom de m�thode d�ja d�fini et � respecter)

		but.addActionListener(this);
	}
					
	

	// Methode qui intercepte toutes les actions se d�roulant dans la classe
	// Re�oit l'�venement d�clencheur en argument
	
	public  void actionPerformed(ActionEvent e)
	{
		// Test sur la source de l'�v�nement
		// Ici le test aurait pu etre supprim� etant donn� qu'il n'y �
		// q'un seul bouton dans ma classe
		
		if (e.getSource() == but)
		{
			// Cr�ation d'une boite de dialogue de type bDial d�riv�e de JDial
			// Le constructeur attend comme argument une fenetre et une partie
			bDial dial = new bDial(this,part);
			dial.setSize(200,150);
			
			// Recupere la taille de l'�cran et positionne lla bDial au milieu
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			dial.setLocation((screenSize.width-dial.getWidth())/2,(screenSize.height-dial.getHeight())/2);
			
			dial.setVisible(true);
			
			// Lance la partie
			part.commencer();
		}
	}
	
}



/**************************************************************************
 
   Classe bDial permettant d'instancier un objet JDialog personnalis�
   pour demander le nom du joueur et lui donner le choix du symbole.
   
   Elle contient des labels, un champ texte, une combo (liste d�roulante),
   un bouton et une r�f�rence � la partie en cours.   

**************************************************************************/


class bDial extends JDialog implements ActionListener
{
	private JButton	butOk;
	private JLabel		lNom,lSymb,lRegl;
	private JTextField	fNom;
	private JComboBox	bSymb;
	private Partie		_part;
	
	// Constructeur de bDial
	// Argument 1 : La fenetre � laquelle la bDial appartient
	// Argument 2 : La partie en cours
	
	public bDial(PanelMain panelMain,Partie part)
	{	
		// Appel du constructeur de la classe de base (JDialog)
		super ();

		_part = part;
		butOk = new JButton("Ok");
		lNom = new JLabel("  Votre nom :");
		fNom = new JTextField(10);
		fNom.setText("Moi");
		lSymb = new JLabel("  Symbole :");
		lRegl = new JLabel("      Le rond commence la partie !");
		String tSymb[] = { "Croix", "Rond" };
		bSymb = new JComboBox(tSymb);
		bSymb.setSelectedIndex(1);
		
		JPanel haut = new JPanel();
		JPanel bas = new JPanel();
		JPanel mil = new JPanel();
		Container  c = getContentPane();
		c.add(haut,"North");
		c.add(mil);
		c.add(bas,"South");
		
		haut.setLayout(new GridLayout(2,2,5,5));
		haut.add(lNom);
		haut.add(fNom);
		haut.add(lSymb);
		haut.add(bSymb);
		
		mil.add(lRegl);
		
		bas.add(butOk);
		butOk.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		// Cr�ation du joueur dans la partie
		_part.createJoueurs((int)bSymb.getSelectedIndex(), fNom.getText());
		
		setVisible(false);
		
		// Destruction de l'objet bDial
		dispose();
	}	
}


/***********************************************************
 
   Classe Joueur permettant de cr�er des joueurs.
   Ses deux membres sont le nom et le symbole du joueur.
 
************************************************************/

class Joueur
{
	private int 		_symb;
	private String		_nom;
	
	// Constructeur de Joueur
	// Re�oit le nom et le symbole et les affectent
	public Joueur(int symb, String nom)
	{
		_symb = symb;
		_nom = nom;		
	}
	
	// M�thode qui renvoit le symbole
	public int getSymb()
	{
		return _symb;
	}
	
	// M�thode qui renvoit le nom
	public String getNom()
	{
		return _nom;
	}
}


/*****************************************************************
 
   Classe Partie permettant de cr�er une partie
   Elle contient :
   	- 4 constantes permettant de rendre le code plus lisible !!
 	- un booleen refl�tant l'�tat de la partie (en cours ou non)
 	- un tableau d'entiers repr�sentant la zone de jeu et l'�tat
 	  des cases (rond, croix, rien) -> tab.
 	- un autre tableau qui contient les diff�rentes combinaisons 
 	  gagnantes -> sol
 	- 1 entier permettant de stocker l'endroit ou l'ordinateur
 	  doit jouer -> strat.
 	- 2 Joueurs.
 	- Une r�f�rence au tableau de Paneau de MorpionFrame
 
******************************************************************/

class Partie
{
	static public final int CROIX	= 0;
	static public final int ROND  	= 1;
	static public final int RIEN  	= 2;
	static public final int NON		= 10;
		
	private boolean 	isRunning = false;
	private int	 	tab[] = { RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN,RIEN };
	private int 		sol[][] = { {0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6} };
	private int 		strat = NON;
	private Joueur  	j1,j2;
	private Paneau		pans[];
	
	// Constructeur de Partie
	// Re�oit la r�f�rence au tableau de Paneau et l'affecte
	public Partie(Paneau t[])
	{
		pans = t;
		
	}
	
	// Cr�ation des Joueurs
	// Re�oit le nom et le symbole choisi par l'utilisateur dans la bDial
	public void createJoueurs(int symb, String nom)
	{
		j1 = new Joueur(symb,nom);
		if (symb == CROIX)
		{
			j2 = new Joueur(ROND,"L'Ordinateur");
			jeuOrdi();
		}
		else
			j2 = new Joueur(CROIX,"L'Ordinateur");
	}
	
	// M�thode qui renvoit le contenu d'une case donn�e de tab
	public int getCase(int _case)
	{
		return tab[_case];	
	}

	// M�thode qui affecte le contenu d'une case donn�e de tab	
	public void setCase(int _case, int symb)
	{
		tab[_case] = symb;
	}
	
	// M�thode qui initialise la partie
	public void commencer()
	{
		isRunning = true;
	}
	
	// M�thode qui fait jouer l'ordinateur
	// Teste si l'ordinateur peut gagner sur ce coup
	// Si oui joue ou il faut grace a ouJouer()
	// Si non, teste si l'ordinateur peut perdre au prochain coup
	// Si oui joue ou il faut pour l'emp�cher grace � ouJouer()
	// Si non teste si il peut essayer de mettre en place une combinaison gagnante
	// Si oui il le fait grace a isStrat()
	// Si non joue al�atoirement
	
	public void jeuOrdi()
	{
		int jouEn;
		
		jouEn = ouJouer(j2.getSymb());
		if (jouEn == NON)
		{
			jouEn = ouJouer(j1.getSymb());
			if (jouEn == NON)
			{
				isStrat();
				if (strat == NON)
				{
					
					Random alea = new Random();
					// G�n�re un nombre entre 0 et 8
					jouEn = alea.nextInt(9);
					
					// Recommence si la case est prise
					while ( tab[jouEn] != RIEN )
						jouEn = alea.nextInt(9);
				}
				else
					jouEn = strat;
			}
		}
		
		// Met tab �  jour		
		tab[jouEn] = j2.getSymb();
		
		// Dessine sur le Paneau correspondant
		pans[jouEn].setSymbo(j2.getSymb());
		pans[jouEn].repaint();
		
		// Appel de la m�thode qui interroge les r�gles
		// pour voir si la partie est finie
		// Lui transmet le joueur
		intRegl(j2);
		
	}
	
	
	// M�thode qui interroge les r�gles en transmettant 
	// le dernier symbole jou�
	// Re�oit un joueur en argument
	// Si un joueur � gagn� ou qu'il y a match nul : propose de rejouer
	public void intRegl(Joueur j)
	{
		int result = regles(j.getSymb());			
		
		if (result == 1)
		{
			JOptionPane.showMessageDialog(null,j.getNom() + " a Gagn� !");

			int rep = JOptionPane.showConfirmDialog(null,"Voulez vous rejouer ?","Partie finie",JOptionPane.YES_NO_OPTION)	;
			propRej(rep);
		}
		else if ( ( j.getNom() != "L'Ordinateur" ) && (result != RIEN) )
					jeuOrdi();
		
		if (result == RIEN)
		{
			int rep = JOptionPane.showConfirmDialog(null,"Voulez vous rejouer ?","Partie finie",JOptionPane.YES_NO_OPTION)	;
			propRej(rep);
		}
	}
	
	// M�thode qui g�re la r�ponse � la proposition de rejouer
	// Attend la r�ponse comme argument
	// Si oui -> remet tout � z�ro
	// Si non -> quitte l'application
	public void propRej(int rep)
	{
		
		if (rep == 0)
		{
			for (int i = 0 ; i < 9 ; i++)
			{
				pans[i].setSymbo(RIEN);
				pans[i].setDejDess(false);
				pans[i].repaint();
				tab[i] = RIEN;
			}
			if (j2.getSymb() == ROND)
				jeuOrdi();
		}
		else
			System.exit(0); 
			
	}
	
	// M�thode qui est appel�e quand l'utilisateur clique sur un Paneau
	// Re�oit une r�f�rence au Paneau en argument
	public void jeu(Paneau p)
	{
		// Teste si le Paneau est d�ja dessin� ou non
		if (p.getDejDess() == false)
		{
			p.setDejDess(true);
			
			// Dessine sur le Paneau et met tab � jour
			p.setSymbo(j1.getSymb());
			tab[p.getIndice()] = j1.getSymb();
			p.repaint();
			
			// Appelle l'interrogation des r�gles pour l'utilisateur
			intRegl(j1);
		}
	}

	// M�thode qui renvoit l'�tat de la partie
	public boolean getIsRunning()
	{
		return isRunning;	
	}
	
	// M�thode qui d�termine si l'ordinateur peut perdre ou gagner au prochain coup
	// Parcours le tableau des combinaisons gagnantes et regarde si
	// le symbole  re�u peut en faire une ou  au prochain coup
	// si oui -> affecte le num�ro de la case d'ou vient le danger/la solution � jouer
	public int ouJouer(int symb)
	{
		int cases = 0,c1,c2,c3;
		int jouer = NON;
		
		while ( (jouer == NON) && (cases < 8) )
		{
			for (cases = 0 ; cases < 8  ; cases++)
			{
				c1 = tab[sol[cases][0]];
				c2 = tab[sol[cases][1]];
				c3 = tab[sol[cases][2]];
				
				if ( (c1 == symb) && ( (c2 == c1) && (c3 == RIEN) ) )
					jouer = sol[cases][2];
				if ( (c1 == symb) && ( (c3 == c1) && (c2 == RIEN) ) )
					jouer = sol[cases][1];
				if ( (c2 == symb) && ( (c2 == c3) && (c1 == RIEN) ) )
					jouer = sol[cases][0];
					
			}
		}
		return jouer;	
	}
	
	
	// M�thode qui d�termine si l'ordinateur peut gagner dans 2 coups
	// Parcours le tableau des combinaisons gagnantes et regarde si
	// il peut en faire une dans 2 coups
	// si oui -> affecte le num�ro d'une des cases gagnantes � strat
	public void isStrat()
	{
		int cases = 0,c1,c2,c3;
		strat = NON;
		
		while ( (strat == NON) && (cases < 8) )
		{
			for (cases = 0 ; cases < 8  ; cases++)
			{
				c1 = tab[sol[cases][0]];
				c2 = tab[sol[cases][1]];
				c3 = tab[sol[cases][2]];
				
				if ( (c1 == j2.getSymb()) && ( (c2 == c3) && (c3 == RIEN) ) )
					strat = sol[cases][2];
				if ( (c1 == j2.getSymb()) && ( (c3 == c2) && (c2 == RIEN) ) )
					strat = sol[cases][1];
				if ( (c2 == j2.getSymb()) && ( (c1 == c3) && (c1 == RIEN) ) )
					strat = sol[cases][0];
			}
		}	
	}
	
	// M�thode qui d�termine si un symbole � gagn� ou si match nul
	// Parcours tab pour voir si il reste des cases vides
	// Puis parcours le tableau des combinaisons gagnantes et regarde si
	// le symbole re�u en argument en a fait une.
	// si oui -> renvoit 1
	// si non -> renvoit 0 si il reste des cases vides ou 2 si match nul
	
	//  Re�oit le symbole du joueur qui vient juste de jouer
	public int regles(int symbCur)
	{
		int cases = 0,c1,c2,c3,parc;
		parc = tab[cases++];
		while ( (parc != RIEN) && (cases < 9) )
			parc = tab[cases++];
		
		for (cases = 0 ; cases < 8  ; cases++)
		{
			c1 = tab[sol[cases][0]];
			c2 = tab[sol[cases][1]];
			c3 = tab[sol[cases][2]];
			
			if ( (c1 == symbCur) && ( (c2 == c1) && (c3 == c1) ) )
				return 1;
		}
		
		if (parc == RIEN)
			return 0;
		else
			return 2;
	}
}


/*****************************************************************
 
   Classe Paneau permettant la cr�ation de JPanel personnalis�s.
   Ils contiennent :
   	- un booleen permettant de savoir si il sont deja dessin�s
   	- une r�f�rence �  la partie en cours
   	- un indice (correspondance avec les cases du tableau tab)
   	- la valeur du symbole dessin� 
 
******************************************************************/

class Paneau extends JPanel
{
	private boolean dejDess = false;
	private Partie _part;
	private int _indice;
	private int symbo = 2;
	
	// Constructeur de Paneau
	// Re�oit une r�f�rence � la partie et son indice
	public Paneau(Partie part, int indice)
	{
		_indice = indice;	
		_part = part;
		setSize(60,60);
		setBackground(Color.lightGray);
		
		// Ajoute un MouseListener (�couteur de souris) qui va
		// intercepter les clicks sur le Paneau
		addMouseListener(new MouseAdapter()
			{
				// D�finition de la m�thode mouseClicked qui sera 
				// automatiquement appel�e quand il y aura un click
				//c'est la commande new MouseAdapter qui permet 
				//de la d�finir de suite
				public void mouseClicked(MouseEvent e)
				{
					if (_part.getIsRunning())
						_part.jeu((Paneau)e.getSource());
				}
			});
	}
	
	// M�thode qui renvoit le symbole du Paneau
	public int getSymbo()
	{
		return symbo;
	}

	// M�thode qui affecte le symbole du Paneau	
	public void setSymbo(int symb)
	{
		symbo = symb;
	}

	// M�thode qui renvoit l'�tat du Paneau (d�ssin� oui / non )
	public boolean getDejDess()
	{
		return dejDess;
	}
	
	// M�thode qui affecte l'�tat du Paneau
	public void setDejDess(boolean etat)
	{
		dejDess = etat;
	}	
	
	// M�thode qui renvoit l'indice du Paneau
	public int getIndice()
	{
		return _indice;	
	}
	
	// M�thode qui est appel� � la cr�ation d'un Paneau et
	// d�s que l'on fait un Paneau.repaint()
	
	public void paintComponent(Graphics g)
	{
		// Appel de la m�thode paintComponent de la classe parente
		super.paintComponent(g);
		
		if (symbo == _part.CROIX)
		{
			// Dessine une croix
			g.setColor(Color.blue);
			
			g.drawLine(5,5,50,50);
			g.drawLine(50,5,5,50);
		}
		if (symbo == _part.ROND)
		{
			// Dessine un rond
			g.setColor(Color.yellow);
			g.drawOval(5,5,this.getWidth()-5,this.getHeight()-5);
		}
	}}
	


