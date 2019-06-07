package gallery;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ImageView extends JPanel {
	private String image;
	
	ImageView(Image image) {
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);
		
		//on crée le panel contenant l'image pleine écran
		JPanel panel_image = new JPanel();
		panel_image.setBackground(Color.BLACK);
		add(panel_image, BorderLayout.CENTER);
		
		//on crée un JPanel contenant les boutons de l'application
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		panel_button.setBackground(Color.BLACK);
				
		//On ajoute du bouton supprimer et le bouton modifier
		JButton btnDelete = new JButton("Supprimer");
		panel_button.add(btnDelete);
		
		JButton btnModify = new JButton("Modifier");
		panel_button.add(btnModify);
		
	}

	
	public void setImage(String image) {
		this.image = image;
		
	}
}
