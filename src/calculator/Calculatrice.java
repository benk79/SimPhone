package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calculatrice extends JPanel {

	/**
	 * Open source code download from the internet, restructured design to integrate the screen
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel container = new JPanel(new BorderLayout());

	String[] tab_string = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/" };

	JButton[] tab_button = new JButton[tab_string.length];
	private JLabel ecran = new JLabel();
	private Dimension dim = new Dimension(50, 40);
	private Dimension dim2 = new Dimension(50, 31);
	private double chiffre1;
	private boolean clicOperateur = false, update = false;
	private String operateur = "";

	public Calculatrice() {

		this.setSize(240, 260);
		this.setBorder(BorderFactory.createEmptyBorder(-15, -30, -15, -15));

		
		initComposant();
	
		this.add(container);
		container.setBackground(Color.BLACK);
		this.setVisible(true);

	}

	private void initComposant() {
		
		
		
		Font police = new Font("Arial", Font.BOLD, 20);
		ecran = new JLabel("0");
		ecran.setFont(police);
		ecran.setForeground(Color.red);
		ecran.setHorizontalAlignment(JLabel.CENTER);
		ecran.setPreferredSize(new Dimension(150, 50));
	
		Color turquoise = new Color(1, 185, 179);
		JPanel operateur = new JPanel();
		operateur.setPreferredSize(new Dimension(80, 300));
		operateur.setOpaque(true);
		operateur.setBackground(turquoise);
		
		Color turquoise2 = new Color(69, 138, 136);
		JPanel chiffre = new JPanel();
		chiffre.setPreferredSize(new Dimension(230, 300));
		chiffre.setOpaque(true);
		chiffre.setBackground(turquoise2);
		
		JPanel panEcran = new JPanel();
		Color turquoise3 = new Color(189, 225, 224);
		panEcran.setPreferredSize(new Dimension(220, 50));
		panEcran.setOpaque(true);
		panEcran.setBackground(turquoise3);



		for (int i = 0; i < tab_string.length; i++) {
			tab_button[i] = new JButton(tab_string[i]);

			
			tab_button[i].setFont(tab_button[i].getFont().deriveFont(22f));

			tab_button[i].setBackground(tab_button[i].getBackground().brighter());

			tab_button[i].setPreferredSize(dim);
			switch (i) {
			
			case 11:
				tab_button[i].addActionListener(new EgalListener());
				chiffre.add(tab_button[i]);
				break;
			case 12:
				tab_button[i].setForeground(Color.red);
				tab_button[i].addActionListener(new ResetListener());
				operateur.add(tab_button[i]);
				break;
			case 13:
				tab_button[i].addActionListener(new PlusListener());
				tab_button[i].setPreferredSize(dim2);
				operateur.add(tab_button[i]);
				break;
			case 14:
				tab_button[i].addActionListener(new MoinsListener());
				tab_button[i].setPreferredSize(dim2);
				operateur.add(tab_button[i]);
				break;
			case 15:
				tab_button[i].addActionListener(new MultiListener());
				tab_button[i].setPreferredSize(dim2);
				operateur.add(tab_button[i]);
				break;
			case 16:
				tab_button[i].addActionListener(new DivListener());
				tab_button[i].setPreferredSize(dim2);
				operateur.add(tab_button[i]);
				break;
			default:
				
				chiffre.add(tab_button[i]);
				tab_button[i].addActionListener(new ChiffreListener());
				break;
			}
		}
		panEcran.add(ecran);
		panEcran.setBorder(BorderFactory.createLineBorder(Color.black));

		container.add(panEcran, BorderLayout.NORTH);
		container.add(chiffre, BorderLayout.WEST);
		container.add(operateur, BorderLayout.EAST);
		container.setBackground(Color.BLACK);
		container.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
	}

	
	private void calcul() {
		if (operateur.equals("+")) {
			chiffre1 = chiffre1 + Double.valueOf(ecran.getText()).doubleValue();
			ecran.setText(String.valueOf(chiffre1));
		}
		if (operateur.equals("-")) {
			chiffre1 = chiffre1 - Double.valueOf(ecran.getText()).doubleValue();
			ecran.setText(String.valueOf(chiffre1));
		}
		if (operateur.equals("*")) {
			chiffre1 = chiffre1 * Double.valueOf(ecran.getText()).doubleValue();
			ecran.setText(String.valueOf(chiffre1));
		}
		if (operateur.equals("/")) {
			try {
				chiffre1 = chiffre1 / Double.valueOf(ecran.getText()).doubleValue();
				ecran.setText(String.valueOf(chiffre1));
			} catch (ArithmeticException e) {
				ecran.setText("0");
			}
		}
	}

	
	class ChiffreListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str = ((JButton) e.getSource()).getText();
			if (update) {
				update = false;
			} else {
				if (!ecran.getText().equals("0"))
					str = ecran.getText() + str;
			}
			ecran.setText(str);
		}
	}

	
	class EgalListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			calcul();
			update = true;
			clicOperateur = false;
		}
	}

	
	class PlusListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre1));
			} else {
				chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "+";
			update = true;
		}
	}

	
	class MoinsListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre1));
			} else {
				chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "-";
			update = true;
		}
	}

	
	class MultiListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre1));
			} else {
				chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "*";
			update = true;
		}
	}

	
	class DivListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (clicOperateur) {
				calcul();
				ecran.setText(String.valueOf(chiffre1));
			} else {
				chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
				clicOperateur = true;
			}
			operateur = "/";
			update = true;
		}
	}

	
	class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			clicOperateur = false;
			update = true;
			chiffre1 = 0;
			operateur = "";
			ecran.setText("");
		}
	}

}
