package gallery;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ImageView extends JPanel {
	private GalleryImage image;

	private JPanel panel_image;

	ImageView (ActionListener cancelListener)
	{
		
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 5, 5, 5));
		setBackground(Color.BLACK);
		
		//on cr�e le panel contenant l'image pleine �cran
		panel_image = new JPanel();
		panel_image.setBackground(Color.BLACK);
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
		this.image = image;
		updateImage();
	}

	public void updateImage ()
	{
		panel_image.removeAll();

		System.out.println(image.getPath());
		JLabel chemin = new JLabel(image.getPath());
		chemin.setBackground(Color.BLACK);
		chemin.setForeground(Color.WHITE);
		panel_image.add(chemin);
		
		ImageIcon icon = new ImageIcon(image.getPath());
		Image img = icon.getImage();
		int height = icon.getIconHeight();
		int width = icon.getIconWidth();
		
		Image newimg;
		if (height > width) {
			int newWidth = 400;
			int newHeight = newWidth * height / width;
			newimg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		} else {
			int newHeight = 300;
			int newWidth = newHeight * width / height;
			newimg = img.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		}

		icon = new ImageIcon(newimg);

		JLabel imagelabel = new JLabel(icon);
		imagelabel.setBackground(Color.BLACK);
		panel_image.setBackground(Color.BLACK);
		panel_image.setForeground(Color.WHITE);
		panel_image.add(imagelabel, BorderLayout.CENTER);
		
		
		
		validate();
		repaint();
	}
}
