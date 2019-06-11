package tictactoe;

import javax.swing.*; 	  // Librairie graphique
import java.awt.*;		  // Composants Java 
import java.awt.event.*; // Permet d'utiliser les événements liés aux composants
import java.util.*;	  // Utilitaires, ici pour la génération de nombres aléatoires

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
		
		// Création de 2 JPanel pour la mise en forme de la fenetre
		haut = new JPanel();
		panel.add(haut);
		bas = new JPanel();
		panel.add(bas, BorderLayout.SOUTH);

		// Le gestionnaire de mise en forme du Panel haut est redéfini
		// en grille de 3 lignes, 3 colonnes avec 2 pixels entre chaques
		haut.setLayout(new GridLayout(3,3,2,2));

		// Création des 9 Paneau et ajout au Panel haut
		for (int  i=0 ; i<9 ; i++)
			{
			pans[i] = new  Paneau(part,i);
			haut.add(pans[i]);
			}
				
		// Définition du bouton, ajout au Panel bas
		but = new JButton("Commencer");
		bas.add(but);

		// Ajout d'un ActionListener (écouteur d'action) sur le bouton
		// Cela va permettre d'intercepter le click par le biais de la 
		// méthode actionPerformed (nom de méthode déja défini et à respecter)

		but.addActionListener(this);
	}
					
	

	// Methode qui intercepte toutes les actions se déroulant dans la classe
	// Reçoit l'évenement déclencheur en argument
	
	public  void actionPerformed(ActionEvent e)
	{
		// Test sur la source de l'évènement
		// Ici le test aurait pu etre supprimé etant donné qu'il n'y à
		// q'un seul bouton dans ma classe
		
		if (e.getSource() == but)
		{
			// Création d'une boite de dialogue de type bDial dérivée de JDial
			// Le constructeur attend comme argument une fenetre et une partie
			bDial dial = new bDial(this,part);
			dial.setSize(200,150);
			
			// Recupere la taille de l'écran et positionne lla bDial au milieu
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			dial.setLocation((screenSize.width-dial.getWidth())/2,(screenSize.height-dial.getHeight())/2);
			
			dial.setVisible(true);
			
			// Lance la partie
			part.commencer();
		}
	}
	
}



/**************************************************************************
 
   Classe bDial permettant d'instancier un objet JDialog personnalisé
   pour demander le nom du joueur et lui donner le choix du symbole.
   
   Elle contient des labels, un champ texte, une combo (liste déroulante),
   un bouton et une référence à la partie en cours.   

**************************************************************************/


class bDial extends JDialog implements ActionListener
{
	private JButton	butOk;
	private JLabel		lNom,lSymb,lRegl;
	private JTextField	fNom;
	private JComboBox	bSymb;
	private Partie		_part;
	
	// Constructeur de bDial
	// Argument 1 : La fenetre à laquelle la bDial appartient
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
		// Création du joueur dans la partie
		_part.createJoueurs((int)bSymb.getSelectedIndex(), fNom.getText());
		
		setVisible(false);
		
		// Destruction de l'objet bDial
		dispose();
	}	
}


/***********************************************************
 
   Classe Joueur permettant de créer des joueurs.
   Ses deux membres sont le nom et le symbole du joueur.
 
************************************************************/

class Joueur
{
	private int 		_symb;
	private String		_nom;
	
	// Constructeur de Joueur
	// Reçoit le nom et le symbole et les affectent
	public Joueur(int symb, String nom)
	{
		_symb = symb;
		_nom = nom;		
	}
	
	// Méthode qui renvoit le symbole
	public int getSymb()
	{
		return _symb;
	}
	
	// Méthode qui renvoit le nom
	public String getNom()
	{
		return _nom;
	}
}


/*****************************************************************
 
   Classe Partie permettant de créer une partie
   Elle contient :
   	- 4 constantes permettant de rendre le code plus lisible !!
 	- un booleen reflétant l'état de la partie (en cours ou non)
 	- un tableau d'entiers représentant la zone de jeu et l'état
 	  des cases (rond, croix, rien) -> tab.
 	- un autre tableau qui contient les différentes combinaisons 
 	  gagnantes -> sol
 	- 1 entier permettant de stocker l'endroit ou l'ordinateur
 	  doit jouer -> strat.
 	- 2 Joueurs.
 	- Une référence au tableau de Paneau de MorpionFrame
 
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
	// Reçoit la référence au tableau de Paneau et l'affecte
	public Partie(Paneau t[])
	{
		pans = t;
		
	}
	
	// Création des Joueurs
	// Reçoit le nom et le symbole choisi par l'utilisateur dans la bDial
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
	
	// Méthode qui renvoit le contenu d'une case donnée de tab
	public int getCase(int _case)
	{
		return tab[_case];	
	}

	// Méthode qui affecte le contenu d'une case donnée de tab	
	public void setCase(int _case, int symb)
	{
		tab[_case] = symb;
	}
	
	// Méthode qui initialise la partie
	public void commencer()
	{
		isRunning = true;
	}
	
	// Méthode qui fait jouer l'ordinateur
	// Teste si l'ordinateur peut gagner sur ce coup
	// Si oui joue ou il faut grace a ouJouer()
	// Si non, teste si l'ordinateur peut perdre au prochain coup
	// Si oui joue ou il faut pour l'empécher grace à ouJouer()
	// Si non teste si il peut essayer de mettre en place une combinaison gagnante
	// Si oui il le fait grace a isStrat()
	// Si non joue aléatoirement
	
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
					// Génère un nombre entre 0 et 8
					jouEn = alea.nextInt(9);
					
					// Recommence si la case est prise
					while ( tab[jouEn] != RIEN )
						jouEn = alea.nextInt(9);
				}
				else
					jouEn = strat;
			}
		}
		
		// Met tab à  jour		
		tab[jouEn] = j2.getSymb();
		
		// Dessine sur le Paneau correspondant
		pans[jouEn].setSymbo(j2.getSymb());
		pans[jouEn].repaint();
		
		// Appel de la méthode qui interroge les règles
		// pour voir si la partie est finie
		// Lui transmet le joueur
		intRegl(j2);
		
	}
	
	
	// Méthode qui interroge les règles en transmettant 
	// le dernier symbole joué
	// Reçoit un joueur en argument
	// Si un joueur à gagné ou qu'il y a match nul : propose de rejouer
	public void intRegl(Joueur j)
	{
		int result = regles(j.getSymb());			
		
		if (result == 1)
		{
			JOptionPane.showMessageDialog(null,j.getNom() + " a Gagné !");

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
	
	// Méthode qui gère la réponse à la proposition de rejouer
	// Attend la réponse comme argument
	// Si oui -> remet tout à zéro
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
	
	// Méthode qui est appelée quand l'utilisateur clique sur un Paneau
	// Reçoit une référence au Paneau en argument
	public void jeu(Paneau p)
	{
		// Teste si le Paneau est déja dessiné ou non
		if (p.getDejDess() == false)
		{
			p.setDejDess(true);
			
			// Dessine sur le Paneau et met tab à jour
			p.setSymbo(j1.getSymb());
			tab[p.getIndice()] = j1.getSymb();
			p.repaint();
			
			// Appelle l'interrogation des règles pour l'utilisateur
			intRegl(j1);
		}
	}

	// Méthode qui renvoit l'état de la partie
	public boolean getIsRunning()
	{
		return isRunning;	
	}
	
	// Méthode qui détermine si l'ordinateur peut perdre ou gagner au prochain coup
	// Parcours le tableau des combinaisons gagnantes et regarde si
	// le symbole  reçu peut en faire une ou  au prochain coup
	// si oui -> affecte le numéro de la case d'ou vient le danger/la solution à jouer
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
	
	
	// Méthode qui détermine si l'ordinateur peut gagner dans 2 coups
	// Parcours le tableau des combinaisons gagnantes et regarde si
	// il peut en faire une dans 2 coups
	// si oui -> affecte le numéro d'une des cases gagnantes à strat
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
	
	// Méthode qui détermine si un symbole à gagné ou si match nul
	// Parcours tab pour voir si il reste des cases vides
	// Puis parcours le tableau des combinaisons gagnantes et regarde si
	// le symbole reçu en argument en a fait une.
	// si oui -> renvoit 1
	// si non -> renvoit 0 si il reste des cases vides ou 2 si match nul
	
	//  Reçoit le symbole du joueur qui vient juste de jouer
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
 
   Classe Paneau permettant la création de JPanel personnalisés.
   Ils contiennent :
   	- un booleen permettant de savoir si il sont deja dessinés
   	- une référence à  la partie en cours
   	- un indice (correspondance avec les cases du tableau tab)
   	- la valeur du symbole dessiné 
 
******************************************************************/

class Paneau extends JPanel
{
	private boolean dejDess = false;
	private Partie _part;
	private int _indice;
	private int symbo = 2;
	
	// Constructeur de Paneau
	// Reçoit une référence à la partie et son indice
	public Paneau(Partie part, int indice)
	{
		_indice = indice;	
		_part = part;
		setSize(60,60);
		setBackground(Color.lightGray);
		
		// Ajoute un MouseListener (écouteur de souris) qui va
		// intercepter les clicks sur le Paneau
		addMouseListener(new MouseAdapter()
			{
				// Définition de la méthode mouseClicked qui sera 
				// automatiquement appelée quand il y aura un click
				//c'est la commande new MouseAdapter qui permet 
				//de la définir de suite
				public void mouseClicked(MouseEvent e)
				{
					if (_part.getIsRunning())
						_part.jeu((Paneau)e.getSource());
				}
			});
	}
	
	// Méthode qui renvoit le symbole du Paneau
	public int getSymbo()
	{
		return symbo;
	}

	// Méthode qui affecte le symbole du Paneau	
	public void setSymbo(int symb)
	{
		symbo = symb;
	}

	// Méthode qui renvoit l'état du Paneau (déssiné oui / non )
	public boolean getDejDess()
	{
		return dejDess;
	}
	
	// Méthode qui affecte l'état du Paneau
	public void setDejDess(boolean etat)
	{
		dejDess = etat;
	}	
	
	// Méthode qui renvoit l'indice du Paneau
	public int getIndice()
	{
		return _indice;	
	}
	
	// Méthode qui est appelé à la création d'un Paneau et
	// dès que l'on fait un Paneau.repaint()
	
	public void paintComponent(Graphics g)
	{
		// Appel de la méthode paintComponent de la classe parente
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
	


