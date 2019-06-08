package gallery;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ImageView extends JPanel {
	private String image;

	private JPanel panel_image;

	ImageView (ActionListener cancelListener)
	{
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);
		
		//on cr�e le panel contenant l'image pleine �cran
		panel_image = new JPanel();
		//panel_image.setBackground(Color.BLACK);
		add(panel_image, BorderLayout.CENTER);
		
		
		
		//on cr�e un JPanel contenant les boutons de l'application
		JPanel panel_button = new JPanel();
		add(panel_button, BorderLayout.SOUTH);
		panel_button.setBackground(Color.BLACK);

		//On ajoute du bouton annuler
		JButton btnCancel = new JButton("Annuler");
		panel_button.add(btnCancel);
		btnCancel.addActionListener(cancelListener);

		//On ajoute du bouton supprimer et le bouton modifier
		JButton btnDelete = new JButton("Supprimer");
		panel_button.add(btnDelete);
		
		JButton btnModify = new JButton("Modifier");
		panel_button.add(btnModify);
	}

	
	public void setImage(GalleryImage image) {
		this.image = image.getPath();
		updateImage();
	}

	public void updateImage ()
	{
		panel_image.removeAll();

		System.out.println(image);
		panel_image.add(new JLabel(image));
		
		ImageIcon imageFull = new ImageIcon(image);
		JLabel img = new JLabel(imageFull);
		add(img, BorderLayout.CENTER);
	}
}
